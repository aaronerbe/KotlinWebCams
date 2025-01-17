import kotlinx.coroutines.runBlocking

/**
 *This function gets latitude or longitude from the user.  In order to reduce duplication, it takes in as an input from the program which it is looking for and handles accordingly (taking advantage of when).
 * In addition, it verifies that the data received is viable.  if not, it re-prompts the user
*/
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

/**
 * This function checks the given lat/lon to see if it produces viable list of webcams.
 * Returns Boolean
*/
fun checkLatLonWebCams(lat: Double, lon: Double): Boolean{
    val webCams = buildData(lat, lon)
    return webCams.checkLatLon()
}

/**
 * Creates webCam object.  Initializes it (fetches the data)
 * Returns webCams object for use
 */
fun buildData(lat: Double, lon: Double): WebCams = runBlocking {
    //create webCams object based off class
    val webCams = WebCams(lat, lon)
    webCams.init() // Fetch data
    webCams
}

/**
 * Gets User input of which webCam ID they are interested in.
 * Validates ID.  Calls webCams method to validate that it actually returns webcam details
 * Returns ID
 */
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

/**
 * Asks user if they want to exit the program or continue
 * Validates correct input
 * Returns Boolean
 */
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

/**
 * Main Function
 * Loops until user exits
 * Loops Calls getLatLon(), checkLatLonWebCams until valid data
 * Call buildData to create webCams object and initialize with Lat/Lon
 * Presents user with titles of webcams in given area using webCams method
 * Gets webcam ID input from user
 * Presents user with webcam details
 * Asks if user wants to exit
 */
fun main() {
    var exit = false
    while(!exit){
        var lat: Double
        var lon: Double
        do{
            println()
            lat = getLatLon("lat")
            lon = getLatLon("lon")
            if(!checkLatLonWebCams(lat, lon)){
                println("No available WebCams at that location.  Please try again")
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