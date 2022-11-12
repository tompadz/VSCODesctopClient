package components.main_window_left

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.models.ProfileModel
import consts.Colors
import consts.Icons
import utils.ProfileListViewType
import views.ErrorView
import views.ProfileView

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun MainProfileList(
    listHeadersIndex:MutableList<Int>,
    state: LazyListState,
    isLoading:Boolean,
    error:Throwable?,
    isLastPage:Boolean,
    items:List<ProfileListViewType>,
    onLoadMoreClick:() -> Unit,
    onProfileClick:(profile:ProfileModel) -> Unit,
    onPageFilterClick:() -> Unit,
) {


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
                                listHeadersIndex.add(index)
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Colors.field)
                                            .padding(6.dp)
                                    ) {
                                        val text = "page #${viewType.page + 1}"
                                        Text(
                                            text = text,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .alpha(0.3f)
                                                .fillMaxWidth()
                                        )

                                        Image(
                                            painter = painterResource(Icons.sort),
                                            contentDescription = "Setings",
                                            modifier = Modifier
                                                .padding(start = 10.dp)
                                                .size(20.dp)
                                                .alpha(if (!isLoading) 0.5f else 0.1f)
                                                .clickable {
                                                    if (!isLoading) {
                                                        onPageFilterClick()
                                                    }
                                                }
                                                .align(Alignment.CenterStart)
                                        )
                                    }
                                    Divider()
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
                                        .padding(start = 70.dp)
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