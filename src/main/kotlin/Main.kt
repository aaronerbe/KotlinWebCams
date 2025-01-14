import kotlinx.coroutines.runBlocking


fun main() {
    runBlocking {
        val webCams = WebCam(lat = 37.7749, lon = -122.4194) // Example: San Francisco coordinates
        webCams.init() // Fetch data
        println(webCams.data) // Print the fetched webcam data

        //        webCam.data?.let { printMap(it, "   ") }

    }
}