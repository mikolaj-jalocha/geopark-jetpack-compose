package com.geoparkcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.geoparkcompose.data.CardData
import com.geoparkcompose.data.DataOrException
import com.geoparkcompose.ui.theme.GeoparkTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LocationsViewModel by viewModels()
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val dataOrException = viewModel.data.value
            ProvideWindowInsets {
                GeoparkTheme {
                    GeoparkApp(dataOrException)
                }
            }

        }
    }
}

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









