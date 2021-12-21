package com.geopark.core.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations.presentation.menu.composables.MenuTopBar
import com.geoparkcompose.ui.menu.MenuScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ContainerScreen(navigateTo: (String) -> Unit) {

    val pagerState = rememberPagerState()


    Scaffold(
        topBar = { MenuTopBar(pagerState = pagerState) }
    ) {

        HorizontalPager(count = 5, state = pagerState) { page ->
            when (page) {
                0 -> MenuScreen(navigateTo = navigateTo)
                1 -> {}
            }
        }

    }

}