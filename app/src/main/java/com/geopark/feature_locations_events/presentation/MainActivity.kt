package com.geopark.feature_locations_events.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.geopark.core.presentation.ContainerScreen
import com.geopark.feature_locations_events.presentation.content.ContentEvent
import com.geopark.feature_locations_events.presentation.content.components.ContentScreen
import com.geopark.feature_locations_events.presentation.list.components.ListScreen
import com.geopark.core.presentation.util.Screen
import com.geopark.ui.theme.GeoparkTheme
import com.geoparkcompose.ui.menu.MenuScreen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @ExperimentalPagerApi
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    @ExperimentalCoilApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            ProvideWindowInsets {
                GeoparkTheme {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ContainerScreen.route
                    ) {

                        composable(route = Screen.ContainerScreen.route) {
                            ContainerScreen(navigateTo = navController::navigate)
                        }
                        composable(route = Screen.MenuScreen.route) {
                            MenuScreen(navigateTo = navController::navigate)
                        }
                        composable(
                            route = Screen.ListScreen.route + "/{locationType}",
                            arguments = listOf(navArgument("locationType") {
                                type = NavType.StringType
                            })
                        ) {
                            ListScreen(
                                navigateTo = navController::navigate,
                                navigateUp = navController::navigateUp
                            )
                        }
                        composable(
                            route = Screen.ContentScreen.route + "/{locationName}",
                            arguments = listOf(navArgument("locationName") {
                                type = NavType.StringType
                            })
                        ) {
                            ContentScreen(
                                onEvent = this@MainActivity::onEvent,
                                navigateUp = navController::navigateUp
                            )
                        }

                    }

                }

            }

        }
    }

    fun onEvent(event: ContentEvent) {
        when (event) {
            is ContentEvent.Call -> {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    this.data = Uri.parse("tel:${event.data}")
                }

                if (intent.resolveActivity(this.packageManager) != null) {
                    this.startActivity(intent)
                }
            }
            is ContentEvent.ShowOnMap -> {


            }


        }
    }
}











