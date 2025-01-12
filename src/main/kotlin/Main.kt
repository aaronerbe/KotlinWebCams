import kotlinx.coroutines.runBlocking
fun printMap(map: Map<String, Any?>, indent: String = "") {
    map.forEach { (key, value) ->
        if (value is Map<*, *>) {
            println("$indent$key:")
            printMap(value as Map<String, Any?>, "$indent  ")
        } else {
            println("$indent$key: $value")
        }
    }
}
fun main() {
    runBlocking {
        val webCam = WebCam(lat = 37.7749, lon = -122.4194) // Example: San Francisco coordinates
        webCam.init() // Fetch data
        println(webCam.data) // Print the fetched webcam data

        webCam.data?.let { printMap(it, "   ") }



    }
}