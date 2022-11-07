package data.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*

object Ktor {

    private const val AUTH_TOKEN = "Bearer 7356455548d0a1d886db010883388d08be84d0c9"
    private const val URL = "https://vsco.co/api/"

    private val ktor = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        defaultRequest {
            url(URL)
            header("Authorization", AUTH_TOKEN)
        }
    }

    val client get() = ktor
}