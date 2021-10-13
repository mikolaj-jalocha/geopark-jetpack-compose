package com.geoparkcompose.ui.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.geoparkcompose.data.CardData


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListBody(
    categoryName: String = "",
    data : List<CardData> = emptyList(),
    onNavigationClick: () -> Unit,
    onItemClick: (CardData) -> Unit
) {
    Column {
        CategoryTopBar(onNavigationClick)
        PlacesScreen(categoryName,data, onItemClick)
    }
}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlacesScreen(categoryName: String, data : List<CardData> = emptyList(), onItemClick: (CardData) -> Unit) {
    Column(Modifier.padding(bottom = 30.dp)) {
        CardsSection(
            title = categoryName,
            items = data,
            isWide = true,
            isHorizontal = true,
            onItemClick = onItemClick
        )
    }
}








