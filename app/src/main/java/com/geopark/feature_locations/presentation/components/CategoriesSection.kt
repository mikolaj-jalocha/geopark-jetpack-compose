package com.geopark.feature_locations.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geopark.R
import com.geopark.feature_locations.domain.util.LocationType

@Preview
@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    locationType: LocationType = LocationType.All,
    onLocationChange: (LocationType) -> Unit = {}
) {

    Column(modifier = modifier) {
        Text(
            text = "Categories",
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 23.dp),
            style = MaterialTheme.typography.h6
        )

        Row {
            CategoriesTabItem(text = "All", iconId = R.drawable.ic_all, isSelected = locationType is LocationType.All, onSelect = { onLocationChange(LocationType.All) })
            CategoriesTabItem(text = "Hotels", iconId = R.drawable.ic_bed, isSelected = locationType is LocationType.Hotel, onSelect = { onLocationChange(LocationType.Hotel)})
            CategoriesTabItem(text = "Restaurants", iconId = R.drawable.ic_restaurant, isSelected = locationType is LocationType.Restaurant, onSelect = { onLocationChange(LocationType.Restaurant) })
            CategoriesTabItem(text = "Explore", iconId = R.drawable.ic_explore, isSelected = locationType is LocationType.Explore, onSelect = { onLocationChange(LocationType.Explore) })
            CategoriesTabItem(text = "Active", iconId = R.drawable.ic_active, isSelected = locationType is LocationType.Active, onSelect = { onLocationChange(LocationType.Active) })
        }

    }


}