//CLIENT IMPORTS
import data.WebCamResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
//REQUEST IMPORTS
import io.ktor.client.request.*
import io.ktor.client.statement.*
//SERIALIZER AND CONTENT NOTIFICATION FOR JSON
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*


//Singleton object to hold the reusable Json configuration.
object JsonConfig {
    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true // Removes unknown keys (player)
    }
}

class WebCams(private val lat: Double = 0.0, private val lon: Double = 0.0) {

    private val API_KEY: String = "hxK1iiN4GCkjymbhFO76k67rzmfQ60M1"
    var data: WebCamResponse? = null // Nullable WebCamResponse to store the parsed data

    //Initializes the WebCams object by fetching and parsing the webcam data.
    suspend fun init() {
        data = fetchWebCamData() // Fetch and parse the data
    }

    //Fetches the webcam data from the Windy API and parses it into a WebCamResponse object.
    private suspend fun fetchWebCamData(): WebCamResponse? {
        val url = buildBaseURL()

        //BUILD THE CLIENT
        val client = HttpClient(CIO) {   //CIO is the 'engine'
            //adds logging plugin
            //install(Logging) {
                //level = LogLevel.HEADERS
            //}
            //add contentnegotiation for handling json data
            install(ContentNegotiation) {
                //calls the method
                json(JsonConfig.json) // Use the singleton Json instance here
            }
        }
        return try {
            // MAKE THE REQUEST
            val response: HttpResponse = client.get(url) {
                expectSuccess = true // Enables default validation of response
                headers {
                    append("Content-Type", "application/json")
                    append("x-windy-api-key", API_KEY)
                }
            }

            if (response.status.value in 200..299) {
                println("Successful response!")

                // DESERIALIZE the JSON response into WebCamResponse
                val responseBody = response.bodyAsText()
                JsonConfig.json.decodeFromString<WebCamResponse>(responseBody) // Use the singleton Json instance here
            } else {
                println("Error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Exception during request: ${e.message}")
            null
        } finally {
            client.close() // CLOSE THE CLIENT
        }
    }

    //Builds a custom URL for accessing the Windy API based on the provided query parameters.
    private fun buildBaseURL(): String {
        return "https://api.windy.com/webcams/api/v3/webcams?offset=0&categoryOperation=or&nearby=${lat},${lon},250&include=categories,images,location,player,urls&categories=water,island,beach,harbor,bay,coast,underwater,mountain,park,sportarea"
    }
}
