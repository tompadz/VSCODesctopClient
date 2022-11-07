package data.providers

import data.models.ProfileModel
import data.models.SearchItem
import data.models.SiteModel
import data.repository.VSCORepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.AppSettings
import utils.LoadingStateListener
import utils.ProfileListViewType
import utils.ResponseResult
import java.net.URLEncoder

class SearchProvider(
    private val listener: LoadingStateListener
) {

    private val repository = VSCORepository

    suspend fun getProfiles(
        page: Int,
        query: String,
    ) : SearchResult {
        return try {

            listener.onLoadingChange(true)

            val result = withContext (Dispatchers.IO) {

                val encodeQuery = URLEncoder.encode(query, "utf-8")

                when (val result = repository.getSearchItems(encodeQuery, page)) {
                    is ResponseResult.Error -> throw result.error
                    is ResponseResult.Success -> {

                        listener.isLastPage(result.value.results.isEmpty())

                        // get profiles array
                        val profiles = convertAllSearchItemsToProfileModel(
                            searchItems = result.value.results,
                        )

                        //get view type array
                        val viewModels = convertAllProfilesToViewTypeList(profiles)

                        listener.onLoadingChange(false)

                        //add header and return data
                        viewModels.apply {
                            add(0, ProfileListViewType.HeaderPage(page))
                        }
                    }
                }
            }

            SearchResult.Success(result)
        }catch (t : Throwable) {
            listener.onLoadingChange(false)
            SearchResult.Error(t)
        }
    }

    private fun convertAllProfilesToViewTypeList(
        profiles:List<ProfileModel>
    ): MutableList<ProfileListViewType> {
        val viewModels = mutableListOf<ProfileListViewType>()
        profiles.forEach {
            viewModels.add(
                ProfileListViewType.Profile(it)
            )
        }
        return viewModels
    }

    private suspend fun convertAllSearchItemsToProfileModel(
        searchItems:List<SearchItem>,
    ): List<ProfileModel> {
        val profiles = mutableListOf<ProfileModel>()

        val showOnlyUserWhoHaveMedia = AppSettings.settings.showOnlyUserWhoHaveMedia
        val minMediaCountToShow = AppSettings.settings.minMediaCountToShow
        val needShowImageCount = AppSettings.settings.needShowImageCount

        if (needShowImageCount || showOnlyUserWhoHaveMedia) {
            when {
                (needShowImageCount && showOnlyUserWhoHaveMedia) || (!needShowImageCount && showOnlyUserWhoHaveMedia) -> {
                    searchItems.forEach {
                        val profileMedia = getProfileMedia(
                            it.siteId
                        )

                        if (profileMedia != null && profileMedia.media!!.size >= minMediaCountToShow) {
                            profiles.add(
                                ProfileModel(
                                    id = it.userId,
                                    image = it.responsive_url ?: "https://via.placeholder.com/150.png",
                                    name = it.userName,
                                    url = it.siteDomain,
                                    imageCount = profileMedia.media.size,
                                    siteId = it.siteId,
                                    imagesLargerThanMax = profileMedia.next_cursor != null
                                )
                            )
                        }
                    }
                }
                needShowImageCount && !showOnlyUserWhoHaveMedia -> {
                    searchItems.forEach {

                        val profileMedia = getProfileMedia(
                            it.siteId
                        )

                        if (profileMedia != null) {
                            profiles.add(
                                ProfileModel(
                                    id = it.userId,
                                    image = it.responsive_url ?: "https://via.placeholder.com/150.png",
                                    name = it.userName,
                                    url = it.siteDomain,
                                    imageCount = profileMedia.media!!.size,
                                    siteId = it.siteId,
                                    imagesLargerThanMax = profileMedia.next_cursor != null
                                )
                            )
                        }

                    }
                }
            }
        } else {

            searchItems.forEach {
                profiles.add(
                    ProfileModel(
                        id = it.userId,
                        image = it.responsive_url ?: "https://via.placeholder.com/150.png",
                        name = it.userName,
                        url = it.siteDomain,
                        imageCount = 0,
                        siteId = it.siteId,
                        imagesLargerThanMax = false
                    )
                )
            }
        }

        return profiles
    }


    private suspend fun getProfileMedia(siteId: Long):SiteModel?  {
        return try {
            val result = withContext(Dispatchers.IO) {
                repository.getProfileMedia(siteId)
            }
            when (result) {
                is ResponseResult.Error -> throw result.error
                is ResponseResult.Success -> result.value
            }
        } catch (t: Throwable) {
            println(t.message)
            null
        }
    }

    sealed class SearchResult {
        data class Success(val items: List<ProfileListViewType>) : SearchResult()
        data class Error(val error: Throwable) : SearchResult()
    }

}