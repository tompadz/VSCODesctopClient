package data.repository

import com.google.gson.Gson
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import utils.ResponseResult

open class BaseRepository {
    suspend inline fun <reified T> fetchData(
        noinline request : suspend () -> HttpResponse,
    ) : ResponseResult<T> {
        var result : ResponseResult<T>? = null
        flow<T> {
            val response = request.invoke()
            val body = response.bodyAsText()
            val jsonBody = Gson().fromJson(body, T::class.java)
            emit(jsonBody)
        }.catch { throwable ->
            println(throwable.message.toString())
            result = ResponseResult.Error(throwable)
        }.collect { data ->
            result = ResponseResult.Success(data)
        }
        return result !!
    }
}