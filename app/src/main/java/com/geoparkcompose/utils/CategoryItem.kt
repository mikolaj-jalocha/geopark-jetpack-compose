package com.geoparkcompose.utils

import androidx.annotation.DrawableRes

data class CategoryItem(
    @DrawableRes
    val iconId : Int,
    val type : CategoryType,
)
data class DamiContentItem(
    @DrawableRes
    val iconId : Int,
    val title  : String
)

