package com.geopark.feature_locations_events.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geopark.ui.theme.CinnabarRed


@Composable
fun CategoriesTabItem(
    text: String,
    iconId: Int,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor = animateColorAsState(if (isSelected) CinnabarRed else Color.White)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

            Box(modifier = Modifier
                .padding(start = 16.dp, bottom = 0.dp, top = 16.dp, end = 16.dp)
                .clip(CircleShape)
                .clickable {
                    onSelect()
                }
                .background(backgroundColor.value)
                .size(39.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    modifier = Modifier
                        .size(22.dp),
                    contentDescription = text,
                    tint = if (isSelected) Color.White else Color.Black,
                )

            }

        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp, bottom = 0.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.Gray,
        )
    }
}