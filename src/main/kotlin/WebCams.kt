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
    private var data: WebCamResponse? = null // Nullable WebCamResponse to store the parsed data

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

    //Get Titles + webcamID
    fun printTitles() {
        println()
        if (data != null) {
            println("Total webcams: ${data!!.total}")
            for (webcam in data!!.webcams) {
                println("Title: ${webcam.title} - WebCam ID:  ${webcam.webcamId}")
            }
        } else {
            println("No data to present.")
        }
    }

    //Get Titles + webcamID
    fun printDetailsByWebCamID(webCamID: Long) {
        println()
        if (data == null){
            println("No data avaialble for webCamId: $webCamID")
            return
        }
        //Find the webcam by ID
        val webcam = data!!.webcams.find { it.webcamId == webCamID}
        if(webcam != null){
            val title = webcam.title
            val windyUrl = webcam.urls.detail
            val providerUrl = webcam.urls.provider
            val imagePreview = webcam.images.current.preview
            val city = webcam.location.city
            val country = webcam.location.country
        //Print the Details
        println("Details for $title (ID: $webCamID)")
        println("City: $city, Country: $country")
        println("Windy URL: $windyUrl")
        println("Provider URL: $providerUrl")
        println("Image Preview URL: $imagePreview")
        } else {
            println("No webcam found with ID: $webCamID")
        }
    }

    //Get check if valid webcam ID
    fun isValidID(webCamID: Long): Boolean {
        return data?.webcams?.any { it.webcamId == webCamID } ?: false
        //data?.webcams? handles if it comes back null
        //checks if any elements exist in the list for that id
        //?: is an 'Elvis' operator.  gives a fallback if the expression returns null.  so it will return false if webcamId returns a null value
    }
}
