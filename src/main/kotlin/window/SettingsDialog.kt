package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import utils.AppSettings
import utils.AppUtils
import consts.Colors
import consts.Theme
import java.awt.Dimension
import java.net.URI

@Composable
fun SettingsDialog(
    onCloseRequest: () -> Unit
) {

    var needShowImageCount: Boolean by remember { mutableStateOf(AppSettings.settings.needShowImageCount) }
    var showOnlyUserWhoHaveMedia: Boolean by remember { mutableStateOf(AppSettings.settings.showOnlyUserWhoHaveMedia) }
    var minMediaCountToShow: Int by remember { mutableStateOf(AppSettings.settings.minMediaCountToShow) }

    fun saveSettings() {
        val settings = AppSettings.Settings(
            needShowImageCount, showOnlyUserWhoHaveMedia, minMediaCountToShow
        )
        AppSettings.changeSettings(settings)
        onCloseRequest()
    }

    MaterialTheme(
        colors = Theme.colors()
    ) {
        Dialog(
            onCloseRequest = onCloseRequest,
            title = "Settings",
            resizable = false,
            state = rememberDialogState(width = 350.dp, height = 400.dp)
        ) {

            window.minimumSize = Dimension(350, 400)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                //needShowImageCount
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = "Show profile media count"
                        )
                        Text(
                            text = "Shows how many media in each profile, significantly increases loading time",
                            modifier = Modifier
                                .alpha(0.3f)
                                .padding(top = 5.dp),
                            style = TextStyle(
                                fontSize = 12.sp
                            )
                        )
                    }

                    Switch(
                        checked = needShowImageCount,
                        onCheckedChange = {
                            needShowImageCount = it
                        },

                        )
                }


                //showOnlyUserWhoHaveMedia
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = "Show only users with medias"
                        )
                        Text(
                            text = "significantly increases loading time",
                            modifier = Modifier
                                .alpha(0.3f)
                                .padding(top = 5.dp),
                            style = TextStyle(
                                fontSize = 12.sp
                            )
                        )
                    }

                    Switch(
                        checked = showOnlyUserWhoHaveMedia,
                        onCheckedChange = {
                            showOnlyUserWhoHaveMedia = it
                        })
                }

                //minMediaCountToShow
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Show profile if media is greater than or equal to",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Spacer(Modifier.width(16.dp))

                    BasicTextField(
                        value = minMediaCountToShow.toString(),
                        onValueChange = {
                            try {
                                minMediaCountToShow = if (it.isBlank() || it.toInt() == 0) {
                                    1
                                }else{
                                    it.toInt()
                                }
                            }catch (_:Throwable){}
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(50.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        Colors.field,
                                        RoundedCornerShape(percent = 15)
                                    )
                                    .padding(vertical = 2.dp, horizontal = 5.dp)
                                    .height(25.dp),
                                contentAlignment = Alignment.Center
                            ){
                                innerTextField()
                            }
                        }
                    )
                }

                Button(
                    onClick = {
                        saveSettings()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)

                ) {
                    Text(
                        text = "Save",
                        color = Color.White
                    )
                }

                Divider(modifier = Modifier
                    .padding(vertical = 16.dp)
                )

                TextButton(
                    onClick = {
                        AppUtils().openInBrowser(URI("https://github.com/tompadz/VSCODesctopClient"))
                    }
                ) {
                    Text(
                        text = "Github",
                        color = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}