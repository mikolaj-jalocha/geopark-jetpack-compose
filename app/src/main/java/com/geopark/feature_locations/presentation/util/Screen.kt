package com.geopark.feature_locations.presentation.util

sealed class Screen(val route: String){
    object MenuScreen : Screen("menu_screen")
    object ListScreen : Screen("list_screen")
    object ContentScreen : Screen("content_screen")
}
