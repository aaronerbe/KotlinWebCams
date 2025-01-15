import kotlinx.coroutines.runBlocking

fun getLatLon(latOrLonString: String): Double{
    while (true) { // Keep looping until we get valid input
        when (latOrLonString){  //conditional when statements
            "lat" -> println("Enter the $latOrLonString of interest:  E.g. 43.5266  ")
            "lon" -> println("Enter the $latOrLonString of interest:  E.g. -116.0571  ")
        }
        val latOrLon = readlnOrNull()?.trim()?.toDoubleOrNull()  //convert to double

        if (latOrLon != null) {
            when (latOrLonString){  //check if they are valid ranges
                "lat" -> if(latOrLon in -90.0..90.0){return latOrLon}
                "lon" -> if(latOrLon in -180.0..180.0){return latOrLon}
            }
        } else {
            println("Invalid $latOrLonString Value. Please enter a valid number.")
        }
    }
}

fun checkLatLonWebCams(lat: Double, lon: Double): Boolean{
    val webCams = buildData(lat, lon)
    return webCams.checkLatLon()
}

fun buildData(lat: Double, lon: Double): WebCams = runBlocking {
    //create webCams object based off class
    val webCams = WebCams(lat, lon)
    webCams.init() // Fetch data
    webCams
}

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
            println()
        }
    }
}

fun getExitInput(): Boolean{
    while(true){
        println()
        println("Would you like to exit the program?  Yes | No")
        val exitInput = readlnOrNull()?.trim()?.lowercase()
        if(exitInput != null){
            when(exitInput){
                "yes" -> return true
                "no" -> return false
            }
        }
        println("Sorry, not a valid input.  Please try again")
    }
}
fun main() {
    var exit = false
    while(exit == false){
        var lat: Double
        var lon: Double
        do{
            println()
            lat = getLatLon("lat")
            lon = getLatLon("lon")
            if(!checkLatLonWebCams(lat, lon)){
                println("No avaialble WebCams at that location.  Please try again")
            }
        }while(!checkLatLonWebCams(lat, lon))

        val webCams = buildData(lat, lon)
        webCams.printTitles()                       //print webcam titles & ids in area
        val webCamID = getWebCamIDInput(webCams)    //get webCamID input
        webCams.printDetailsByWebCamID(webCamID)    //display the webcam details

        exit = getExitInput()
    }

    println("Thanks for using my program!")

}