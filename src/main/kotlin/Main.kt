import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import utils.AppSettings
import window.MainWindow
import java.awt.Dimension


fun main() = application {
    //check settings file
    AppSettings.initSettings()

    val icon = painterResource("logo.png")
    Window(
        onCloseRequest = ::exitApplication,
        title = "VSCO",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        resizable = false,
        icon = icon
    ) {

        MainWindow(this)
    }
}
