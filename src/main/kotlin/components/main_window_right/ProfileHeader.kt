package components.main_window_right

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import components.AsyncImage
import data.models.ProfileModel
import utils.AppUtils
import consts.Icons
import java.net.URI

@Composable
fun ProfileHeader(
    profileModel: ProfileModel,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
          modifier = Modifier.
            padding(horizontal =  16.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Icons.close),
                contentDescription = "Close",
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .clickable {
                        onCloseClick()
                    }
            )

            Spacer(Modifier.width(20.dp))

            AsyncImage(
                url = profileModel.image,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(34.dp)
            )

            Spacer(Modifier.width(15.dp))

            Text(
                text = profileModel.name
            )

            Spacer(
                Modifier
                .weight(1f)
            )

            Image(
                painter = painterResource(Icons.open),
                contentDescription = "open profile",
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .clickable {
                        AppUtils().openInBrowser(
                            URI("https://${profileModel.url}")
                        )
                    }
            )

        }



        Divider()
    }
}