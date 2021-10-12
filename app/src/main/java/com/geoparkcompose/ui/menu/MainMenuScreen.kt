package com.geoparkcompose.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.geoparkcompose.data.CardData
import com.geoparkcompose.ui.composables.CardsSection
import com.geoparkcompose.ui.composables.CategoriesTab
import com.geoparkcompose.ui.composables.MainTopBar
import com.geoparkcompose.utils.CategoryType


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun MainMenuBody(
    onCategoryClick: (String) -> Unit,
    onItemClick: (CardData) -> Unit,
    data: List<CardData>
) {
    var selectedCategory by rememberSaveable { mutableStateOf(CategoryType.All) }

    Column {
        MainTopBar()
        CategoriesTab() { category ->
            selectedCategory = category
        }
        MainCategoryContent(
            selectedCategory.title,
            if (selectedCategory.title != CategoryType.All.title) data.filter { it.type == selectedCategory.title } else data,
            onItemClick,
            onCategoryClick)
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun MainCategoryContent(
    title: String,
    data: List<CardData>,
    onItemClick: (CardData) -> Unit,
    onSeeAllClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .padding(bottom = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CardsSection(
            title, data,
            onItemClick = onItemClick,
            onSeeAllClick = onSeeAllClick
        )
        CardsSection(
            title = "Recently Watched",
            items = listOf(
                CardData()
            ),
            onItemClick = onItemClick,
            onSeeAllClick = onSeeAllClick
        )
        CardsSection(
            title = "Saved", items = listOf(
                CardData()
            ),
            onItemClick = onItemClick,
            onSeeAllClick = onSeeAllClick
        )

    }
}