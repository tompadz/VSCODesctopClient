package components.main_window_left

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.models.ProfileModel
import utils.Colors
import utils.ProfileListViewType
import views.ErrorView
import views.ProfileView

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun MainProfileList(
    isLoading:Boolean,
    error:Throwable?,
    isLastPage:Boolean,
    items:List<ProfileListViewType>,
    onLoadMoreClick:() -> Unit,
    onProfileClick:(profile:ProfileModel) -> Unit
) {

    val state = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (isLoading && items.isEmpty()) {
            CircularProgressIndicator(
                color = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if (error != null) {
            ErrorView(error)
        }

        if (error == null) {
            LazyColumn(state = state) {
                items.forEachIndexed { index, viewType ->
                    when (viewType) {
                        is ProfileListViewType.HeaderPage -> {
                            stickyHeader {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Colors.field)
                                        .padding(6.dp)
                                ) {
                                    val text = "${viewType.page + 1} page"
                                    Text(
                                        text = text,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .alpha(0.3f)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                        is ProfileListViewType.Profile -> {
                            item(viewType.item.id) {
                                ProfileView(
                                    profile = viewType.item
                                ) {
                                    onProfileClick(it)
                                }
                            }
                        }
                    }

                    if (index != items.size - 1 && viewType !is ProfileListViewType.HeaderPage) {
                        if (items.size - 1 >= index + 1 && items[index +1] !is ProfileListViewType.HeaderPage) {
                            item {
                                Divider(
                                    modifier = Modifier
                                        .padding(start = 63.dp)
                                )
                            }
                        }else {
                            item {
                                Divider()
                            }
                        }
                    }
                }

                if (isLoading && items.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
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

                if (!isLastPage && items.isNotEmpty() && !isLoading) {
                    item {
                        TextButton(
                            onClick = {
                                onLoadMoreClick()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text ="Load more",
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}