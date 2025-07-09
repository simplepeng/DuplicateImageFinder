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
            secondary = secondaryDark,
            background = backgroundDark,
            surface = surfaceDark,
            onPrimary = onPrimaryDark,
            onSecondary = onSecondaryDark,
            onBackground = onBackgroundDark,
            onSurface = onSurfaceDark
        )
    } else {
        lightColors(
            primary = primaryLight,
            secondary = secondaryLight,
            background = backgroundLight,
            surface = surfaceLight,
            onPrimary = onPrimaryLight,
            onSecondary = onSecondaryLight,
            onBackground = onBackgroundLight,
            onSurface = onSurfaceLight
        )
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}