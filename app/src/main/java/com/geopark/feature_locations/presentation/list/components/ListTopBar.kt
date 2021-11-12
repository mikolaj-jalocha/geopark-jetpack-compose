package com.geopark.feature_locations.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geopark.R
import com.geopark.feature_locations.presentation.components.RoundedIcon
import com.geopark.feature_locations.presentation.content.components.RoundedContactButton
import com.google.accompanist.insets.systemBarsPadding


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
@Preview
fun ListTopBar(onClick : () -> Unit = {}, onValueChange : (String) -> Unit = {}) {
    Column {
        Row(
            Modifier
                .systemBarsPadding(bottom = false)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedIcon(modifier = Modifier.padding(16.dp),iconId = R.drawable.ic_arrow_back){onClick()}

            var text by remember { mutableStateOf("Search") }
            TextField(
                shape = RoundedCornerShape(10.dp),
                value = text,
                onValueChange = { text = it; onValueChange(it)} )
            
            Image(rememberImagePainter(R.mipmap.ic_geopark_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp))

        }
    }
}