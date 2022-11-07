package data.models

data class ProfileModel(
    val id:Long,
    val siteId:Long,
    val name:String,
    val image:String,
    val url:String,
    val imageCount:Int,
    val imagesLargerThanMax:Boolean = false
)
