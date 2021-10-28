package com.geopark.feature_locations.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
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
                    MenuScreen()
                }

            }

        }
    }
}

/*
@ExperimentalMaterialApi
@Composable
fun GeoparkApp(dataOrException: DataOrException<List<CardData>,Exception>) {
    GeoparkTheme {
        val navController = rememberNavController()

        Box {
            GeoparkNavHost(navController = navController,data = dataOrException.data ?: emptyList())
        }
    }
}
*/









