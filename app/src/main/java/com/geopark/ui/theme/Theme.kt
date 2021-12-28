package com.geopark.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


@Composable
fun GeoparkTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        content = content,
        colors = if (darkTheme) DarkColors else LightColors,
        typography = GeoparkTypography
    )
}

private val DarkColors = darkColors(
    primary = CinnabarRed,
    primaryVariant = MaximumRed,
    secondary = EmpireBlue // or BabyBlue
)

private val LightColors = lightColors(
    primary = CinnabarRed,
    primaryVariant = Red45,
    secondary = DenimBlue,
    secondaryVariant = NavyBlue,
    onSurface = NavyBlue)