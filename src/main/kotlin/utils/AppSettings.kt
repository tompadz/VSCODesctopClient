package utils

import com.google.gson.Gson
import java.io.File

object AppSettings {

    private const val FILE_NAME = "config.txt"
    private var _settings:Settings? = null
    val settings get() = _settings!!

    data class Settings(
        val needShowImageCount:Boolean = false,
        val showOnlyUserWhoHaveMedia:Boolean = false,
        val minMediaCountToShow:Int = 1,
    )

    fun initSettings() {

        val file = File(FILE_NAME)

        if (file.createNewFile()) {
            //file created
            _settings = Settings()
            val text = Gson().toJson(Settings())
            file.writeText(text)
            println("settings file created")
        }else {
            //file already exists
            val settings:Settings = Gson().fromJson(file.readText(), Settings::class.java)
            _settings = settings
            println("settings file read")
        }
    }

    fun changeSettings(settings:Settings) {
        val file = File(FILE_NAME)
        val text = Gson().toJson(settings)
        file.writeText(text)
        _settings = settings
        println("settings file changed")
    }
}