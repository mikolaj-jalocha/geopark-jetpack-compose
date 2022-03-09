package com.geopark.core.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations_events.presentation.events_menu.composables.EventMenuScreen
import com.geopark.feature_locations_events.presentation.menu.composables.MenuTopBar
import com.geoparkcompose.ui.menu.MenuScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ContainerScreen(navigateTo: (String) -> Unit) {

    val pagerState = rememberPagerState(0)


    Scaffold(
        topBar = { MenuTopBar(pagerState = pagerState) }
    ) {
        HorizontalPager(count = 2, state = pagerState) { page ->
            when (page) {
                0 -> MenuScreen(navigateTo = navigateTo)
                1 -> EventMenuScreen()
            }
        }

    }

}