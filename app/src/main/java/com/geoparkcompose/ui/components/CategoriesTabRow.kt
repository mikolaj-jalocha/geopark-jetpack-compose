package com.geoparkcompose.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geoparkcompose.R

import com.geoparkcompose.ui.theme.CinnabarRed
import com.geoparkcompose.ui.theme.GeoparkTheme
import com.geoparkcompose.utils.CategoryItem
import com.geoparkcompose.utils.CategoryType

@Composable
fun CategoriesTab(
        onCategoryClick : (CategoryType) -> Unit
) {

        Column {
            Text(text = "Categories",
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 23.dp),
                style = MaterialTheme.typography.h6)
            CategoriesTabRow(listOf(
                CategoryItem(iconId = R.drawable.ic_all, CategoryType.All),
                CategoryItem(iconId = R.drawable.ic_mountain, CategoryType.Explore),
                CategoryItem(iconId = R.drawable.ic_bed, CategoryType.Hotel),
                CategoryItem(iconId = R.drawable.ic_restaurant, CategoryType.Restaurant),
                CategoryItem(iconId = R.drawable.ic_active, CategoryType.Active),
                CategoryItem(iconId = R.drawable.ic_explore, CategoryType.Workshop)
            )){
                onCategoryClick(it)
            }

        }
}

@Composable
fun CategoriesTabRow(categoryItems : List<CategoryItem>,onClick : (CategoryType) -> Unit) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    LazyRow {
        items(categoryItems.size) {
            CategoriesTabItem(it == selectedItemIndex, categoryItems[it]) {
                selectedItemIndex = it
                onClick(categoryItems[it].type)
            }

        }
    }
}

@Composable
fun CategoriesTabItem(isSelected: Boolean = false, item: CategoryItem, onClick: () -> Unit) {

    val backgroundColor = animateColorAsState(if (isSelected) CinnabarRed else Color.White)

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .padding(start = 16.dp, bottom = 0.dp, top = 16.dp, end = 16.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .background(backgroundColor.value)
            .size(39.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
               painter = painterResource(id = item.iconId),
                modifier = Modifier
                    .size(22.dp),
                contentDescription = item.type.title,
                tint = if (isSelected) Color.White else Color.Black,

                )
        }

        Text(
            text = item.type.title,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp, bottom = 0.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.Gray,
        )
    }
}
