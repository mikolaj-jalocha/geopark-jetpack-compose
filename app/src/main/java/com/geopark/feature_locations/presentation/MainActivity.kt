package com.geopark.feature_locations.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations.presentation.content.components.ContentScreen
import com.geopark.feature_locations.presentation.list.components.ListScreen
import com.geopark.feature_locations.presentation.util.Screen
import com.geopark.ui.theme.GeoparkTheme
import com.geoparkcompose.ui.menu.MenuScreen
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @ExperimentalCoilApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            //val dataOrException = viewModel.data.value
            ProvideWindowInsets {
                GeoparkTheme {
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screen.MenuScreen.route){

                        composable(route = Screen.MenuScreen.route) {
                            MenuScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ListScreen.route +"/{locationType}",
                            arguments = listOf(navArgument("locationType") {type = NavType.StringType})
                        ){
                            ListScreen()
                        }
                        composable(
                            route = Screen.ContentScreen.route + "/{locationName}",
                            arguments = listOf(navArgument("locationName") {type = NavType.StringType})
                        ){
                            ContentScreen()
                        }
                    }


                }

            }

        }
    }
}











