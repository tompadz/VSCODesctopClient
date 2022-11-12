package utils

import java.awt.Desktop
import java.net.URI
import java.util.*


class AppUtils {

    fun openInBrowser(uri: URI) {
        val os = getOS()
        val desktop = Desktop.getDesktop()
        when {
            Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(uri)
            os == OS.MAC -> Runtime.getRuntime().exec("open $uri")
            os == OS.WIN -> Runtime.getRuntime().exec("xdg-open $uri")
            else -> throw RuntimeException("cannot open $uri")
        }
    }

    fun getOS(): OS {
        val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
        return when {
            os.contains("mac") || os.contains("darwin") -> OS.MAC
            os.contains("win") -> OS.WIN
            os.contains("nux") -> OS.LINUX
            else -> OS.OTHER
        }
    }

    enum class OS {
        MAC,
        WIN,
        LINUX,
        OTHER,
    }
}