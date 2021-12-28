package com.geopark.feature_locations_events.presentation.menu.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geopark.ui.theme.BabyBlue

@Composable
fun TileTitleSeeAll(title: String, seeAllClick: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 17.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.h6,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = BabyBlue,
            modifier = Modifier
                .clickable(onClick = { seeAllClick(title) })
                .align(Alignment.CenterVertically)
        )
    }
}