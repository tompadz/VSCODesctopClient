package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import components.AsyncImage
import data.models.SiteMedia
import utils.AppUtils
import consts.Icons
import java.net.URI

@Composable
fun MediaView(
    media:SiteMedia
) {
    when (media.type) {
        "image" -> {
            AsyncImage(
                url = media.image!!.responsive_url,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .padding(1.dp)
                    .clickable {
                        AppUtils().openInBrowser(
                            URI("https://${media.image.responsive_url}")
                        )
                    }
            )
        }
        "video" -> {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .padding(1.dp)
                    .clickable {
                        AppUtils().openInBrowser(
                            URI(media.video!!.playback_url)
                        )
                    }
            ) {

                AsyncImage(
                    url = media.video!!.poster_url + "?time=0&width=1080",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp),
                    needAddHttp = false
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(Icons.movie),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }

            }
        }
    }
}