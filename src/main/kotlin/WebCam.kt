import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

//WebCam class
class WebCam(private val lat: Double = 0.0, private val lon: Double = 0.0) {
    // Class Properties:
    // Private is used to keep variables internal to the class.  This is encapsulation

    // API Key
    private val key: String = "hxK1iiN4GCkjymbhFO76k67rzmfQ60M1"

    // This will store the fetched webcam data
    // Map<String, Any> - Map key value pair.  A key-value structure similar to JSON
    //      -'String' - the key is always a string
    //      -'Any'  - the value can by any time.  TODO maybe this needs to remain String?
    // ? means it's nullable property.  It might not have a value initially
    var data: Map<String, Any>? = null

    //INIT
    suspend fun init(){
        // rather than async, kotlin uses coroutines.
        // suspend function can be called within a coroutine to fetch data async
        data = fetchWebCamData()
    }

    //FETCH WEBCAM DATA
    //Fetch the data.  suspend is kotlins way of async using 'coroutine'


    private suspend fun fetchWebCamData(): Map<String, Any>? {
        val url = buildBaseURL()

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }) // Ignore unknown JSON fields
            }
        }

        return try {
            // Fetch the raw JSON response as a String
            val responseText = client.get(url) {
                headers {
                    append("Content-Type", "application/json")
                    append("x-windy-api-key", key)
                }
            }.bodyAsText()

            // Parse the JSON String into a Map<String, Any>
            val parsedJson = Json.parseToJsonElement(responseText).jsonObject

            // Convert JsonObject to Map<String, Any> and assign to `this.data`
            this.data = parsedJson.toMap()
            this.data
        } finally {
            client.close() // Ensure resources are freed
        }
    }

    // Extension function to convert JsonObject to Map<String, Any>
    private fun JsonObject.toMap(): Map<String, Any> {
        return this.map { (key, value) -> key to value.toString() }.toMap()
    }


//    private suspend fun fetchWebCamData(): Map<String, Any>? {
//        //Build a customer URL
//        val url = buildBaseURL()
//
//        //Create an http client using ktor dependency
//        //cio is corouting IO
//        val client = HttpClient(CIO) {
//            //plugin configures how data is serialized (kotlin -> json) or deserialized (json -> kotlin)
//            //TODO - determine if this is necessary?
//            //needed for the body() function
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true //ignores extra fields in json
//                })
//            }
//        }

        //Do the actual API request and then parse it.
//        return try {
//            // Perform the GET request and parse the response body as Map<String, Any>
//            client.get(url) {
//                //required headers for windy api
//                headers {
//                    append("Content-Type", "application/json")  //expected response type = json
//                    append("x-windy-api-key", key)
//                }
//            }.body() // deserializes (json - kotlin) the response body
//            //parses json reponse body into Map<String, Any>
//        } finally {
//            client.close() //close it up after finished
//        }
//    }

    //BUILD URL
    private fun buildBaseURL(): String {
        //Builds custom URL for accessing API based on query params
        //
        return "https://api.windy.com/webcams/api/v3/webcams?offset=0&categoryOperation=or&nearby=${lat},${lon},250&include=categories,images,location,player,urls&categories=water,island,beach,harbor,bay,coast,underwater,mountain,park,sportarea"

    }

}