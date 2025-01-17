# Overview
### Purpose
Purpose of this program was to learn kotlin and demonstrate basic capabilities of the language.

This program takes coordinates as inputs, returns a list of public webcams in that area and allows the user to select a webcam.  Then the program provides the full list of details including links to the live webcam feeds and thumbnail images.

### Overall Structure
WebCams.kt contains the Webcams class.  Here, I have created functions to fetch data, handle parsing and converting to objects, build the URL based on user inputs of lat and lon and basic getter functions that return details of interest and verify data.

WEbCamResponse.kt defines the classes for the JSON data.  All data is organized and structured in classes to greatly simplify accessing the data and provide full flexibility.  For example, data.webcam.images.current.preview.  Structure definition was greatly simplified using the JSON2Kotlin.com utility.

Main.kt contains the main code.  Specifically the code that directly interacts with the user taking input and returning the data.  All inputs are verified as valid and prompts the user until correct.  

### Key Features Demonstrated
+ Variables (mutable and immutable)
+ Expressions
+ Conditionals
+ Loops
+ Functions
+ Classes
+ Collections
+ Uses of When

### API Functionality
The program:
+ HTTP GET Request to Windy API using Ktor Client + CIO Engine
+ Retrieves JSON Response
+ Parse JSON and store as Objects using Kotlinx Serialization as defined in /data/WebCamResponse.kt

[Software Demo Video](https://youtu.be/sxfON9RTcM8)

# Development Environment
+ IDE:  IntelliJ
+ Main programming language:  Kotlin
+ Additional Dependencies:  Ktor, CIO, Serialization, content-negotiation

# Useful Websites
- [Kotlin Official Documentation](https://kotlinlang.org/)
- [Ktor Documentation](https://ktor.io/docs/client-create-new-application.html)
- [Windy API](https://api.windy.com/)
- [JSON2Kotlin](https://json2kotlin.com/)

# Future Work
- Search by City name instead of lat/lon
- Add option to filter by categories rather than a preset list
- API will return number of views a webcam has had which is an indication of popularity.  Could sort by popularity.  For that matter, could add other sorting features such as sort by name or location.  