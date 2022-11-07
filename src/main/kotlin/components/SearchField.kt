package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.Icons
import utils.Colors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    onSearchPress:(query:String) -> Unit,
    buttonEnable:Boolean = true
) {

    var consumedText by remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .onPreviewKeyEvent {
                        when {
                            (it.isCtrlPressed && it.key == Key.Minus && it.type == KeyEventType.KeyUp) -> {
                                consumedText -= text.length
                                text = ""
                                true
                            }
                            (it.isCtrlPressed && it.key == Key.Equals && it.type == KeyEventType.KeyUp) -> {
                                consumedText += text.length
                                text = ""
                                true
                            }
                            else -> false
                        }
                    },
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                Colors.field,
                                RoundedCornerShape(percent = 20)
                            )
                            .padding(vertical = 5.dp, horizontal = 13.dp)
                            .height(20.dp),
                    ) {
                        Box(Modifier.weight(1f)) {
                            if (text.isEmpty()) Text(
                                "Поиск",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                    fontSize = 14.sp
                                )
                            )
                            innerTextField()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Image(
                painter = painterResource(Icons.search),
                contentDescription = "Search",
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .alpha(if (buttonEnable) 1f else 0.4f)
                    .clickable {
                        onSearchPress(text)
                    }
            )

//            TextButton(
//                onClick = {
//                    onSearchPress(text)
//                },
//                shape = CircleShape,
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = Color.White
//                ),
//                enabled = buttonEnable,
//                contentPadding = PaddingValues(0.dp)
//            ) {
//                Image(
//                    painter = painterResource(Icons.search),
//                    contentDescription = "Search",
//                    modifier = Modifier
//                        .size(24.dp)
//                )
//            }
        }
    }
}