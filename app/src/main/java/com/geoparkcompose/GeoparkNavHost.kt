package com.geoparkcompose

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import coil.annotation.ExperimentalCoilApi
import com.geoparkcompose.data.CardData
import com.geoparkcompose.ui.components.components.CardContentBody
import com.geoparkcompose.ui.composables.CategoryListBody
import com.geoparkcompose.ui.menu.MainMenuBody
import com.geoparkcompose.utils.CategoryType
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun GeoparkNavHost(
    navController: NavHostController,
    data: List<CardData>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = GeoparkScreen.MainMenu.name,
        modifier = modifier
    ) {

        composable(GeoparkScreen.MainMenu.name) {
            MainMenuBody(
                onSeeAllClick = { data -> navigateToCategoryList(navController,categoryName = data,) },
                onItemClick = { data -> navigateToCard(navController, cardData = data) },
                data
            )
        }

        val categoryListName = GeoparkScreen.CategoryList.name
        composable(
            route = "$categoryListName/{categoryName}",
            arguments = listOf(
                navArgument("categoryName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val categoryName = entry.arguments?.getString("categoryName") ?: ""
            CategoryListBody(
                categoryName,
                data = if(categoryName == CategoryType.All.title) data else data.filter { it.type == categoryName },
                { navigateBack(navController) },
                onItemClick = { data -> navigateToCard(navController, cardData = data) }
            )
        }

        val cardContentName = GeoparkScreen.Content.name
        composable(
            route = "$cardContentName/{cardName}/{cardDescription}/{cardLocation}/{cardPhotoPath}",
            arguments = listOf(
                navArgument("cardName") {
                    type = NavType.StringType
                }, navArgument("cardDescription") {
                    type = NavType.StringType
                },
                navArgument("cardLocation") {
                    type = NavType.StringType
                },
                navArgument("cardPhotoPath") {
                    type = NavType.StringType
                }

            )
        ) { entry ->

            val cardName = entry.arguments?.getString("cardName") ?: ""
            val cardDescription = entry.arguments?.getString("cardDescription") ?: ""
            val cardLocation = entry.arguments?.getString("cardLocation") ?: ""
            val cardPhotoPath = entry.arguments?.getString("cardPhotoPath") ?: ""



            CardContentBody(
                title = cardName,
                description = cardDescription,
                location = cardLocation,
                photoPath = cardPhotoPath,
                onNavigationClick = { navigateBack(navController) })
        }

    }
}


@ExperimentalMaterialApi
private fun navigateToCategoryList(
    navController: NavController,
    categoryName: String,
) {
        val route =
            "${GeoparkScreen.CategoryList.name}/$categoryName"
        navController.navigate(route = route)

}


@ExperimentalMaterialApi
private fun navigateToCard(
    navController: NavController,
    cardData: CardData
) {
    cardData.apply {
        navController.navigate(route = "${GeoparkScreen.Content.name}/$name/$description/$mapLocation/${encodeUrl(photo)}")
    }
}

private fun navigateBack(navController: NavController) {
    navController.navigateUp()
}

private fun encodeUrl(url : String) = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())