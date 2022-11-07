package views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import components.AsyncImage
import data.models.ProfileModel
import utils.AppSettings
import utils.AppUtils
import java.net.URI

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun ProfileView(
    profile: ProfileModel,
    onProfileClick:(profile:ProfileModel) -> Unit
) {
    ContextMenuArea(
        items = {
            listOf(
                ContextMenuItem(
                    label = "Открыть в браузере"
                ) {
                    AppUtils().openInBrowser(
                        URI("https://${profile.url}")
                    )
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(
                    RoundedCornerShape(7.dp)
                )
                .fillMaxWidth()
                .clickable(role = Role.Button) {
                    onProfileClick(profile)
                }
        ) {
            Row(
                modifier =
                Modifier.padding(horizontal =  10.dp, vertical = 5.dp)
                    .fillMaxWidth()
            ) {

                AsyncImage(
                    url = profile.image,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(45.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(profile.name, maxLines = 1)
                    if (AppSettings.settings.needShowImageCount) {
                        val counts = if (profile.imagesLargerThanMax) "${profile.imageCount}+" else profile.imageCount.toString()
                        Text("$counts - фото")
                    }else {
                        Text(profile.url)
                    }
                }
            }
        }
    }
}