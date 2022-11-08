package components.main_window_right

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.models.SiteMedia
import data.providers.ProfileProvider
import kotlinx.coroutines.launch
import utils.Colors
import utils.LoadingStateListener
import views.ErrorView
import views.MediaView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileMediaGrid(
    media:List<SiteMedia>,
    isLastPage:Boolean,
    isLoading:Boolean,
    error:Throwable?,
    onLoadMoreClick:() -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (isLoading && media.isEmpty()) {
            CircularProgressIndicator(
                color = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if (!isLoading && media.isEmpty()) {
            Text(
                text = "Empty profile",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if (error != null) {
            ErrorView(error)
        }else {

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
            ) {

                items(media) { item ->
                    MediaView(
                        media = item,
                    )
                }

                if (media.isNotEmpty() && isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(1.dp)
                                .background(Colors.field)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(14.dp),
                                color = Color.DarkGray,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }

                if (media.isNotEmpty() && !isLoading && !isLastPage) {
                    item {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(1.dp)
                                .background(Colors.field)
                                .clickable {
                                    onLoadMoreClick()
                                }
                        ) {
                            Text(
                                text = "Load more",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}