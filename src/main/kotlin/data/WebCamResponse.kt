// USED TO SETUP CLASSES FOR THE JSON FILE.  EACH SECTION GETS ITS OWN CLASS
package data

// WebCamResponse.kt

import kotlinx.serialization.Serializable

@Serializable
data class WebCamResponse(
    val total: Int,
    val webcams: List<WebCam>
)

@Serializable
data class WebCam(
    val title: String,
    val viewCount: Int,
    val webcamId: Long,
    val status: String,
    val lastUpdatedOn: String,
    val categories: List<Category>,
    val images: Images,
    val location: Location,
    val urls: Urls
)

@Serializable
data class Category(
    val id: String,
    val name: String
)

@Serializable
data class Images(
    val current: ImageSet,
    val sizes: ImageSizes,
    val daylight: ImageSet
)

@Serializable
data class ImageSet(
    val icon: String,
    val thumbnail: String,
    val preview: String
)

@Serializable
data class ImageSizes(
    val icon: Size,
    val thumbnail: Size,
    val preview: Size
)

@Serializable
data class Size(
    val width: Int,
    val height: Int
)

@Serializable
data class Location(
    val city: String,
    val region: String,
    val region_code: String,
    val country: String,
    val country_code: String,
    val continent: String,
    val continent_code: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class Urls(
    val detail: String,
    val edit: String,
    val provider: String
)
