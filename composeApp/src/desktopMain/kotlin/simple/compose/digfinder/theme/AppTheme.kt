package simple.compose.digfinder.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    typography: Typography = MaterialTheme.typography,
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit,
) {
    val colors = if (isSystemInDarkTheme()) {
        darkColors(
            primary = primaryDark,
            primaryVariant = primaryContainerDark,
            secondary = secondaryDark,
            secondaryVariant = secondaryContainerDark,
            background = backgroundDark,
            surface = surfaceDark,
            error = errorDark,
            onPrimary = onPrimaryDark,
            onSecondary = onSecondaryDark,
            onBackground = onBackgroundDark,
            onSurface = onSurfaceDark,
            onError = onErrorDark
        )
    } else {
        lightColors(
            primary = primaryLight,
            primaryVariant = primaryContainerLight,
            secondary = secondaryLight,
            secondaryVariant = secondaryContainerLight,
            background = backgroundLight,
            surface = surfaceLight,
            error = errorLight,
            onPrimary = onPrimaryLight,
            onSecondary = onSecondaryLight,
            onBackground = onBackgroundLight,
            onSurface = onSurfaceLight,
            onError = onErrorLight
        )
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}