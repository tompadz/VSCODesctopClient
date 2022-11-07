package data.models

data class SearchResponse(
    val page: Int,
    val size: Int,
    val results: List<SearchItem>
)

