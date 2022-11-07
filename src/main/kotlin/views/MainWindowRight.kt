package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import components.main_window_right.ProfileHeader
import components.main_window_right.ProfileMediaGrid
import data.models.ProfileModel
import data.models.SiteMedia
import data.providers.ProfileProvider
import kotlinx.coroutines.launch
import utils.LoadingStateListener

@Composable
fun MainWindowRight(
    profile:ProfileModel,
    onCloseClick:() -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    var isLoading: Boolean by remember { mutableStateOf(false) }
    var isLastPage: Boolean by remember { mutableStateOf(false) }
    var error: Throwable? by remember { mutableStateOf(null) }
    var media : MutableList<SiteMedia> by remember { mutableStateOf(mutableListOf()) }
    var isFirstFetch: Boolean by remember { mutableStateOf(true) }
    var cursor:String by remember { mutableStateOf("") }

    val listener = object : LoadingStateListener {
        override fun onLoadingChange(state: Boolean) {
            isLoading = state
        }

        override fun isLastPage(state: Boolean) {
            isLastPage = state
        }
    }

    val provider = ProfileProvider(profile.siteId , listener)

    fun getMedia() {
        coroutineScope.launch {
            when(val result = provider.getMedia(cursor)) {
                is ProfileProvider.MediaResult.Error -> {
                    error = result.error
                }
                is ProfileProvider.MediaResult.Success -> {
                    cursor = result.cursor ?: ""
                    val newMedia = mutableListOf<SiteMedia>().apply {
                        addAll(media)
                        addAll(result.items)
                    }
                    media = newMedia
                }
            }
        }
    }

    if (isFirstFetch) {
        isFirstFetch = !isFirstFetch
        getMedia()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ProfileHeader(profile, onCloseClick)

        ProfileMediaGrid(
            media = media,
            isLoading = isLoading,
            isLastPage = isLastPage,
            error = error,
            onLoadMoreClick = {
                getMedia()
            }
        )
    }


}