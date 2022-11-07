package utils

import data.models.ProfileModel

sealed class ProfileListViewType {
    data class Profile(val item:ProfileModel):ProfileListViewType()
    data class HeaderPage(val page:Int):ProfileListViewType()
}
