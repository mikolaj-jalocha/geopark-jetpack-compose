package com.geopark.feature_locations.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.ui.theme.BabyBlue


// TODO: implement sorting mechanism
@Composable
fun TileTitleSortBy(title : String, onSortByClick : () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 17.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
        )

        // TODO: implement icons for sorting by
        Row {
            Text(
                text = "Sort by",
                style = MaterialTheme.typography.h6,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = BabyBlue,
                modifier = Modifier
                    .clickable(onClick = { onSortByClick() })
                    .align(Alignment.CenterVertically)
            )
            // TODO: Change icon type
            Icon(
                imageVector  = Icons.Outlined.List,
                contentDescription = null,
                modifier = Modifier.padding(start = 4.dp, end = 16.dp)
            )
        }
    }
}