package com.geopark.core.presentation.util

sealed class Screen(val route: String){
    object MenuScreen : Screen("menu_screen")
    object ListScreen : Screen("list_screen")
    object ContentScreen : Screen("content_screen")
    object ContainerScreen : Screen("container_screen")
}
