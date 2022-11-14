package views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import components.main_window_left.MainHeader
import components.main_window_left.MainProfileList
import data.models.ProfileModel
import data.providers.SearchProvider
import kotlinx.coroutines.launch
import utils.LoadingStateListener
import utils.ProfileListViewType
import window.ScrollToDialog

@Composable
@Preview
fun MainWindowLeftMenu(
    onProfileChange: (profile: ProfileModel) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLoadingChange:(state:Boolean) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    var needShowFilter: Boolean by remember { mutableStateOf(false) }

    var isLoading: Boolean by remember { mutableStateOf(false) }
    var isLastPage: Boolean by remember { mutableStateOf(false) }
    var error: Throwable? by remember { mutableStateOf(null) }

    var searchQuery: String by remember { mutableStateOf("") }
    var listPage: Int by remember { mutableStateOf(0) }
    var listHeadersIndex : MutableList<Int> by remember { mutableStateOf(mutableListOf()) }

    var profiles: MutableList<ProfileListViewType> by remember { mutableStateOf(mutableListOf()) }

    val listener = object : LoadingStateListener {
        override fun onLoadingChange(state: Boolean) {
            isLoading = state
        }

        override fun isLastPage(state: Boolean) {
            isLastPage = state
        }
    }

    val provider = SearchProvider(listener)

    fun getUsersByQuery(
        query: String = searchQuery,
        page: Int = listPage
    ) {
        listHeadersIndex = mutableListOf()
        error = null
        listPage = page
        searchQuery = query
        coroutineScope.launch {
            val result = provider.getProfiles(
                page = listPage,
                query = searchQuery,
            )
            when (result) {
                is SearchProvider.SearchResult.Error -> error = result.error
                is SearchProvider.SearchResult.Success -> {
                    val newList = profiles.apply {
                        addAll(result.items)
                    }
                    profiles = newList
                }
            }
        }
    }

    if (needShowFilter) {
        ScrollToDialog(
            currentPage = listPage,
            onPageChange = {
                needShowFilter = false
                if (it <= listPage) {
                    val itemIndex = listHeadersIndex[it - 1]
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(itemIndex)
                    }
                }else {
                    listPage = it
                    getUsersByQuery(page = listPage)
                }
            },
            onCloseRequest = {
                needShowFilter = false
            }
        )
    }

    Column {
        MainHeader(
            loadingState = isLoading,
            onSettingsClick = onSettingsClick,
            onSearchPress = {
                if (it.isNotBlank()) {
                    profiles = mutableListOf()
                    listPage = 0
                    getUsersByQuery(it)
                }
            }
        )

        MainProfileList(
            items = profiles,
            isLoading = isLoading,
            error = error,
            isLastPage = isLastPage,
            onLoadMoreClick = {
                getUsersByQuery(page = ++listPage)
            },
            onProfileClick = onProfileChange,
            onPageFilterClick = {
                needShowFilter = true
            },
            state = scrollState,
            listHeadersIndex = listHeadersIndex
        )
    }
}