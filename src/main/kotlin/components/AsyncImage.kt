package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.Colors
import java.io.ByteArrayInputStream

@Composable
fun AsyncImage(
    url: String,
    modifier: Modifier,
    contentDisposition: String? = null,
    needAddHttp:Boolean = true
) {

    var image: ImageBitmap? by rememberSaveable {
        mutableStateOf(null)
    }

    val imageBtmp: ImageBitmap? by produceState(image) {
        if (value == null) {
            try {
                value = HttpClient(CIO).use {
                    withContext(Dispatchers.IO) {
                        val _url = if (needAddHttp) "https://$url" else url
                        val response = it.get(_url)
                        ByteArrayInputStream(response.readBytes())
                    }
                }
                    .use(::loadImageBitmap)
                    .also {
                        it.prepareToDraw()
                        image = it
                    }
            }
            catch (t:Throwable) {
                if (t.cause != null)
                    println(t.cause?.message)
            }
        }
    }

    if (imageBtmp == null) {
        Box(
            modifier
                .background(Colors.field)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(14.dp),
                color = Color.DarkGray,
                strokeWidth = 2.dp
            )
        }
    }

    AnimatedVisibility(
        visible =  imageBtmp != null,
        enter = fadeIn(animationSpec = tween(750)),
        exit = fadeOut(animationSpec = tween(750))
    ) {
        Image(
            bitmap = imageBtmp!!,
            contentDescription = contentDisposition,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}
