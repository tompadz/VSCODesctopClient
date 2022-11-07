package utils

sealed class ResponseResult<out T> {
    data class Success<out T>(val value: T): ResponseResult<T>()
    data class Error(val error : Throwable): ResponseResult<Nothing>()
}