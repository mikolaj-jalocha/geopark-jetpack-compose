package com.geopark.feature_locations.presentation.content.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun RoundedContactButton(contentDescription : String ="", iconId : Int, onClick : () -> Unit) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 30.dp)) {

        Card(shape = CircleShape,
            elevation = 0.dp,
            modifier = Modifier.size(50.dp),
            onClick = onClick,
            backgroundColor = Color.Gray.copy(alpha = 0.1f)) {
            Icon(
                rememberImagePainter(iconId),
                contentDescription = contentDescription,
                modifier = Modifier.requiredSize(30.dp),
                tint = Color.Black.copy(alpha = 0.7f))
        }
    }
}