package data.providers

import data.models.SiteMedia
import data.repository.VSCORepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.LoadingStateListener
import utils.ResponseResult
import java.net.URLEncoder

class ProfileProvider (
    private val siteId:Long,
    private val listener: LoadingStateListener
) {

    private val repository = VSCORepository

    suspend fun getMedia(cursor:String):MediaResult {
        var nextCursor:String? = null
        return try {
            listener.onLoadingChange(true)
            val result = withContext(Dispatchers.IO) {
                val encodeCursor = URLEncoder.encode(cursor, "utf-8")
                when (val result = repository.getProfileMedia(siteId, encodeCursor)) {
                    is ResponseResult.Error -> throw result.error
                    is ResponseResult.Success -> {
                        nextCursor = result.value.next_cursor
                        listener.isLastPage(nextCursor == null)
                        listener.onLoadingChange(false)
                        result.value.media!!
                    }
                }
            }
            MediaResult.Success(result, nextCursor)
        }catch (t:Throwable) {
            listener.onLoadingChange(false)
            MediaResult.Error(t)
        }
    }

    sealed class MediaResult {
        data class Success(val items: List<SiteMedia>, val cursor: String?) : MediaResult()
        data class Error(val error: Throwable) : MediaResult()
    }
}