package com.geopark.feature_locations_events.presentation.content.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.geopark.R
import com.geopark.feature_locations_events.presentation.components.RoundedIcon
import com.google.accompanist.insets.systemBarsPadding

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ContentTopBar(onNavigationClick: () -> Unit) {
    Row(
        modifier = Modifier
            .systemBarsPadding(bottom = false)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundedIcon(iconId = R.drawable.ic_arrow_back, iconSize = 22.dp, iconTint = Color.DarkGray, onClick = { onNavigationClick() })
        RoundedIcon(iconId = R.drawable.ic_bookmark_outline, iconSize = 22.dp, iconTint = Color.DarkGray, onClick = {})
    }
}