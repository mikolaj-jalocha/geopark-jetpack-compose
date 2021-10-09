package com.geoparkcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


@Composable
fun GeoparkTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        content = content,
        colors = if (darkTheme) LightColors else LightColors,
        typography = GeoparkTypography
    )
}

private val LightColors = lightColors(
    primary = Red57,
    primaryVariant = Red45,
    secondary = DenimBlue,
    secondaryVariant = NavyBlue,

)