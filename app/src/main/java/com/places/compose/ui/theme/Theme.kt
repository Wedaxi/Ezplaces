package com.places.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = PrimaryDark,
    secondary = Secondary,
    secondaryVariant = Secondary,
    surface = PrimaryDark,
    background = SecondaryDark,
    onPrimary = BrandColorDark,
    onSecondary = BrandColorDark,
    onBackground = BrandColorDark,
    onSurface = BrandColorDark
)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    secondary = Secondary,
    secondaryVariant = SecondaryVariant,
    surface = BrandColor,
    background = BrandColor,
    onPrimary = BrandColor,
    onSecondary = BrandColor,
    onBackground = Primary,
    onSurface = Primary
)

@Composable
fun ComposeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}