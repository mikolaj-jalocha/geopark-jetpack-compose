package com.geoparkcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.geoparkcompose.R


private val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.W400),
    Font(R.font.poppins_medium, FontWeight.W500),
    Font(R.font.poppins_semibold, FontWeight.W600),
    Font(R.font.poppins_thin, FontWeight.W100),
    Font(R.font.poppins_light, FontWeight.W300),
    Font(R.font.poppins_extra_light, FontWeight.W200)
)

private val Manier = FontFamily(
    Font(R.font.manier_medium, FontWeight.W500),
    Font(R.font.manier_regular, FontWeight.W400),
    Font(R.font.manier_light, FontWeight.W300)
)

val GeoparkTypography = Typography(

    h1 = TextStyle(
        fontFamily = Manier,
        fontWeight = FontWeight.Light,
        fontSize = 88.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = Manier,
        fontWeight = FontWeight.Light,
        fontSize = 59.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = Manier,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp

    ),

    h4 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 33.sp,
        letterSpacing = 0.25.sp
    ),

    h5 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp
    ),
    h6 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)