package com.geoparkcompose.ui.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geoparkcompose.R
import com.geoparkcompose.data.CardData
import com.geoparkcompose.utils.CategoryItem
import com.geoparkcompose.utils.DamiContentItem


@ExperimentalMaterialApi
@Composable
fun CategoryListBody(
    title: String = "",
    onNavigationClick: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onItemClick: (CardData) -> Unit
) {
    Column {
        CategoryTopBar(onNavigationClick)
        PlacesScreen(title, onItemClick)
    }
}


@ExperimentalMaterialApi
@Composable
fun PlacesScreen(t: String = "", onItemClick: (CardData) -> Unit) {
    Column(Modifier.padding(bottom = 30.dp)) {
        CardsSection(
            title = t,
            items = listOf( CardData()
            ),
            isWide = true,
            isHorizontal = true,
            onItemClick = onItemClick
        )
    }
}








