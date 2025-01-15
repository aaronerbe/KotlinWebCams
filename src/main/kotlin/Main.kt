import kotlinx.coroutines.runBlocking


fun buildData(lat: Double, lon: Double){
    runBlocking {
        //create webCams object based off class
        val webCams = WebCams(lat, lon)
        //initialize it
        webCams.init() // Fetch data
        //capture the data to use
        val data = webCams.data
        //test print it
        println(webCams.data)

        //Present the data.  check if it's null first
        if(data !=null){
            //iterate through the list of webcams (see WebCamResponse.kt for defined classes for the deserialized json data)
            for(webcams in data.webcams){
                println("Title: ${webcams.title}")
            }

        }

    }
}

fun main() {
    val lat: Double = 37.7749
    val lon: Double = -122.4194

    buildData(lat, lon)
}