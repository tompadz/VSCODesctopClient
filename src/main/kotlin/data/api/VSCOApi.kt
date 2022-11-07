package data.api

import data.client.Ktor
import io.ktor.client.request.*
import io.ktor.client.statement.*

class VSCOApi {

    suspend fun search(query:String, page:Int): HttpResponse {
        return Ktor.client.get("2.0/search/grids?query=$query&page=$page&size=10")
    }

    suspend fun getProfileMedia(siteId:Long, cursor:String):HttpResponse {
        return Ktor.client.get("3.0/medias/profile?site_id=$siteId&limit=14&cursor=$cursor")
    }

}