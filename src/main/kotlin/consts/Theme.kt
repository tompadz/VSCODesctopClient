package consts

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


object Theme {

    @Composable
    fun colors() = Colors(
        primary = Color(0xFF000000),
        primaryVariant = MaterialTheme.colors.primaryVariant,
        secondary = Color(0xFF000000),
        secondaryVariant = Color(0xFF000000),
        background = MaterialTheme.colors.background,
        surface = MaterialTheme.colors.surface,
        error = MaterialTheme.colors.error,
        isLight = MaterialTheme.colors.isLight,
        onBackground = MaterialTheme.colors.onBackground,
        onError = MaterialTheme.colors.onError,
        onPrimary = MaterialTheme.colors.onPrimary,
        onSecondary = MaterialTheme.colors.onSecondary,
        onSurface = MaterialTheme.colors.onSurface,
    )
}
