package com.geoparkcompose.ui.components.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun RoundedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    iconSize: Dp = 20.dp,
    onClick: () -> Unit
) {

    Card(onClick = onClick,
        shape = CircleShape,
        elevation = 4.dp,
        modifier = modifier.size(40.dp)) {
        Icon(
            painter = painterResource(id =iconId),
            contentDescription = null,
            modifier = Modifier.requiredSize(iconSize),
            tint = Color.Gray
        )
    }
}

@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}

@ExperimentalMaterialApi
@Composable
fun ContactItem(title : String, iconId : Int, onClick : () -> Unit) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 30.dp)) {

        Card(shape = CircleShape,
            elevation = 0.dp,
            modifier = Modifier.size(50.dp),
            onClick = onClick,
            backgroundColor = Color.Gray.copy(alpha = 0.1f)) {
            Icon(painterResource(id = iconId),
                contentDescription = title,
                modifier = Modifier.requiredSize(30.dp),
                tint = Color.Black.copy(alpha = 0.7f))
        }
    }
}