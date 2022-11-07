package data.models

data class SiteModel(
    val media : List<SiteMedia>?,
    val next_cursor:String?
)

data class SiteMedia(
    val image: MediaImage?,
    val video: MediaVideo?,
    val type:String,
)

data class MediaVideo(
    val _id:String,
    val poster_url:String,
    val playback_url:String
)

data class MediaImage(
    val _id:String,
    val responsive_url:String
)