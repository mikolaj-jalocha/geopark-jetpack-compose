package com.geopark.core.presentation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations_events.presentation.events_list.composables.EventListScreen
import com.geopark.feature_locations_events.presentation.menu.composables.MenuTopBar
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

    val pagerState = rememberPagerState(0)


    Scaffold(
        topBar = { MenuTopBar(pagerState = pagerState) }
    ) {
        HorizontalPager(count = 5, state = pagerState) { page ->
            when (page) {
                0 -> MenuScreen(navigateTo = navigateTo)
                1 -> {
                    Log.d("HORIZONTAL_PAGER", "Event screen's been loaded ")
                    EventListScreen(navigateTo = navigateTo)
                }
            }
        }

    }

}