package components.main_window_left

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import components.SearchField
import utils.Icons

@Preview
@Composable
fun MainHeader(
    loadingState:Boolean,
    onSearchPress:(query:String) -> Unit,
    onSettingsClick:() -> Unit
) {

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Icons.settings),
                contentDescription = "Setings",
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .alpha(if (!loadingState) 1f else 0.4f)
                    .clickable {
                        onSettingsClick()
                    }
            )

            Spacer(Modifier.width(14.dp))

            SearchField(
                onSearchPress = { onSearchPress(it) },
                buttonEnable = !loadingState
            )
        }
        Divider()
    }
}