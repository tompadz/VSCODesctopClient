package data.repository

import data.api.VSCOApi
import data.models.SearchResponse
import data.models.SiteModel
import utils.ResponseResult

object VSCORepository : BaseRepository() {

    private val api = VSCOApi()

    suspend fun getSearchItems(query: String, page:Int): ResponseResult<SearchResponse> =
        fetchData {
            api.search(query, page)
        }

    suspend fun getProfileMedia(siteId:Long, cursor:String = ""): ResponseResult<SiteModel> =
        fetchData {
            api.getProfileMedia(siteId, cursor)
        }

}