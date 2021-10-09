package com.geoparkcompose.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.geoparkcompose.R
import com.geoparkcompose.ui.components.components.RoundedIcon
import com.google.accompanist.insets.systemBarsPadding

@ExperimentalMaterialApi
@Composable
fun CategoryTopBar(onClick : () -> Unit) {
    Column() {
        Row(
            Modifier.systemBarsPadding(bottom = false).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedIcon(Modifier.padding(16.dp), iconId = R.drawable.ic_arrow_back){onClick()}
            Image(rememberImagePainter(R.mipmap.ic_geopark_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp))
        }

    }
}