package com.geopark.core.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import coil.annotation.ExperimentalCoilApi
import com.geopark.core.presentation.components.TopBar
import com.geopark.feature_locations_events.presentation.events_list.composables.EventListScreen
import com.geoparkcompose.ui.menu.MenuScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ContainerScreen(navigateTo: (String) -> Unit) {

    val topBarTabState = remember {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = { TopBar(){topBarTabState.value = it} }
    ) {
        when (topBarTabState.value) {
            0 -> MenuScreen(navigateTo = navigateTo)
            1 -> EventListScreen(navigateTo = navigateTo)
        }
    }

}