package window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import consts.Colors
import consts.Theme

@Composable
fun ScrollToDialog(
    currentPage:Int,
    onCloseRequest: () -> Unit,
    onPageChange:(page:Int) -> Unit
) {

    var minMediaCountToShow: Int by remember { mutableStateOf(currentPage) }

    MaterialTheme(
        colors = Theme.colors()
    ) {

        Dialog(
            onCloseRequest = onCloseRequest,
            title = "Settings",
            resizable = false,
            state = rememberDialogState(width = 350.dp, height = 150.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Scroll to",
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
                            .width(100.dp),
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
                        onPageChange(minMediaCountToShow)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)

                ) {
                    Text(
                        text = "Apply",
                        color = Color.White
                    )
                }

            }
        }
    }
}