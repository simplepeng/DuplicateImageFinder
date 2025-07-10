package simple.compose.digfinder.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle


@Composable
fun AppTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = MaterialTheme.typography,
    content: @Composable () -> Unit,
) {
//    val colors = if (isSystemInDarkTheme()) {
//        darkColorScheme(
//            primary = primaryDark,
//            onPrimary = onPrimaryDark,
//            secondary = secondaryDark,
//            secondaryVariant = secondaryContainerDark,
//            background = backgroundDark,
//            surface = surfaceDark,
//            error = errorDark,
//            onPrimary = onPrimaryDark,
//            onSecondary = onSecondaryDark,
//            onBackground = onBackgroundDark,
//            onSurface = onSurfaceDark,
//            onError = onErrorDark
//        )
//    } else {
//        lightColors(
//            primary = primaryLight,
//            primaryVariant = primaryContainerLight,
//            secondary = secondaryLight,
//            secondaryVariant = secondaryContainerLight,
//            background = backgroundLight,
//            surface = surfaceLight,
//            error = errorLight,
//            onPrimary = onPrimaryLight,
//            onSecondary = onSecondaryLight,
//            onBackground = onBackgroundLight,
//            onSurface = onSurfaceLight,
//            onError = onErrorLight
//        )
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = typography,
        content = content,
    )
}

object AppTheme {

    val dialogTitle: TextStyle
        @Composable get() = MaterialTheme.typography.titleMedium

    val textFiled: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium
}