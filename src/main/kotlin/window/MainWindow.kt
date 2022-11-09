package window

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import data.models.ProfileModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.AppUtils
import utils.Colors
import views.MainWindowLeftMenu
import views.MainWindowRight
import views.SettingsDialog
import java.net.URI

@Composable
@Preview
fun MainWindow(window: FrameWindowScope) {

    val coroutineScope = rememberCoroutineScope()

    var currentProfile: ProfileModel? by remember { mutableStateOf(null) }
    var showSettingsDialog: Boolean by remember { mutableStateOf(false) }
    var isLoading: Boolean by remember { mutableStateOf(false) }

    if (showSettingsDialog) {
        SettingsDialog {
            showSettingsDialog = false
        }
    }

    window.apply {
        MenuBar {
            Menu("Settings") {
                Item("Open settings", onClick = {
                    if (!showSettingsDialog && !isLoading) {
                        showSettingsDialog = true
                    }
                })
                Item("Open github", onClick = {
                    AppUtils().openInBrowser(URI("https://github.com/tompadz/VSCODesctopClient"))
                })
            }
        }
    }

    MaterialTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(Color.White)
            ) {
                MainWindowLeftMenu(
                    onProfileChange = {
                        coroutineScope.launch {
                            if (currentProfile != null) {
                                currentProfile = null
                                delay(10)
                                currentProfile = it
                            }else {
                                currentProfile = it
                            }
                        }

                    },
                    onSettingsClick = {
                        if (!showSettingsDialog && !isLoading) {
                            showSettingsDialog = true
                        }
                    },
                    onLoadingChange = {
                        isLoading = it
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Divider(
                color = Colors.gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Colors.gray)
            ) {
                if (currentProfile != null) {
                    MainWindowRight(
                        profile = currentProfile!!,
                        onCloseClick = {
                            currentProfile = null
                        }
                    )
                } else {
                    Text(
                        text = "Select profile",
                        style = TextStyle(
                            fontSize = 12.sp
                        ),
                        color = Color.Gray,
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 5.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }

}