import kotlinx.coroutines.runBlocking

fun getWebCamIDInput(webCams: WebCams): Long {
    while (true) { // Keep looping until we get valid input
        println()
        println("Enter the WebCam ID of interest:")
        val webCamID = readlnOrNull()?.trim()?.toLongOrNull()

        if (webCamID != null && webCams.isValidID(webCamID)) {
            //check if it's an actual ID in the list
            return webCamID // Return the valid ID
        } else {
            println("Invalid WebCam ID. Please enter a valid number.")
        }
    }
}

fun buildData(lat: Double, lon: Double){
    runBlocking {
        //create webCams object based off class
        val webCams = WebCams(lat, lon)
        webCams.init() // Fetch data
        webCams.printTitles()
        //get webCamID input and then display the details for it
        val webCamID = getWebCamIDInput(webCams)
        webCams.printDetailsByWebCamID(webCamID)
    }
}

fun main() {
    val lat: Double = 37.7749
    val lon: Double = -122.4194

    buildData(lat, lon)


}