package com.sevban.contextandlifecycle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.sevban.composetutorialssevbanbayir.ui.theme.Pink40
import com.sevban.composetutorialssevbanbayir.ui.theme.Pink80
import com.sevban.composetutorialssevbanbayir.ui.theme.Purple40
import com.sevban.composetutorialssevbanbayir.ui.theme.Purple80
import com.sevban.composetutorialssevbanbayir.ui.theme.PurpleGrey40
import com.sevban.composetutorialssevbanbayir.ui.theme.PurpleGrey80


private val DarkColorPalette = darkColors(
    background = Color(0XFF333333),
    primary = Purple80,
    primaryVariant = PurpleGrey80,
    secondary = Pink80
)

private val LightColorPalette = lightColors(
    primary = Purple40,
    primaryVariant = PurpleGrey40,
    secondary = Pink40,
)

@Composable
fun ContextAndLifecycleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocaDimension provides Dimensions(),
        LocalTextSize provides TextSize()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            content = content
        )
    }
}
