package data.models

data class SearchItem(
    val siteId: Long,
    val userId: Long,
    val responsive_url: String?,
    val userName: String,
    val siteDomain: String
)