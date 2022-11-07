package utils

interface LoadingStateListener {
    fun onLoadingChange(state:Boolean)
    fun isLastPage(state: Boolean)
}