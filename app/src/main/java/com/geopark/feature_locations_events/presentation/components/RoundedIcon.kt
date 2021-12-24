package com.geopark.feature_locations_events.presentation.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun RoundedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    iconSize: Dp = 20.dp,
    iconTint : Color = Color.Black,
    onClick: () -> Unit
) {

    Card(onClick = onClick,
        shape = CircleShape,
        elevation = 4.dp,
        modifier = modifier.size(iconSize*2)) {
        Icon(
            painter = rememberImagePainter(iconId),
            contentDescription = null,
            modifier = Modifier.requiredSize(iconSize),
            tint = iconTint
        )
    }
}