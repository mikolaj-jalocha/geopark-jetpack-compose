package com.geopark.feature_locations_events.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.geopark.core.presentation.ContainerScreen
import com.geopark.core.presentation.util.Screen
import com.geopark.feature_locations_events.presentation.content.ContentEvent
import com.geopark.feature_locations_events.presentation.content.components.ContentScreen
import com.geopark.feature_locations_events.presentation.list.components.ListScreen
import com.geopark.ui.theme.GeoparkTheme
import com.geoparkcompose.ui.menu.MenuScreen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {


      val viewModel : MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewModel.applyCaching()

        setContent {

            ProvideWindowInsets {
                GeoparkTheme {

                    val scaffoldState = rememberScaffoldState()
                    LaunchedEffect(key1 = true) {
                        viewModel.eventFlow.collectLatest { event ->
                            when (event) {
                                is UiEvent.ShowSnackbar -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = event.message
                                    )
                                }
                            }
                        }
                    }
                    Scaffold (scaffoldState = scaffoldState){
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











