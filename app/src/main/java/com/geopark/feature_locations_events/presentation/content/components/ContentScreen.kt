package com.geopark.feature_locations_events.presentation.content.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geopark.R
import com.geopark.feature_locations_events.presentation.content.ContentEvent
import com.geopark.feature_locations_events.presentation.content.ContentViewModel
import com.geopark.ui.theme.BabyBlue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.material.MaterialTheme.colors

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun ContentScreen(
    viewModel: ContentViewModel = hiltViewModel(),
    onEvent: (event: ContentEvent) -> Unit,
    navigateUp: () -> Unit
) {

    val state = viewModel.locationsState.value
    val composableScope = rememberCoroutineScope()

    composableScope.launch {
        viewModel.eventFlow.collectLatest { event ->
            onEvent(event)
        }
    }
    Scaffold {
        Column {
            Box {
                Image(
                    painter = rememberImagePainter(if(state.photos.isNotEmpty()) state.photos[0].url else ""),
                    contentDescription = "Main photography",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                ContentTopBar(navigateUp)
            }

            LazyColumn(
                modifier = Modifier
                    .offset(y = (-50).dp)
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxWidth()
                    .background(colors.surface),
            ) {

                item {
                    Text(
                        text = state.location.name,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                    )
                    Text(
                        text = state.location.address,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .alpha(0.3f)
                            .padding(start = 20.dp)
                    )

                    Button(
                        onClick = {
                            //TODO implement opening navigation
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = BabyBlue,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp)
                    ) {
                        Text(
                            text = "Navigate",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = state.location.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                    )

                    // TODO: 19.09.2021 Adjust better color for text
                    Text(
                        text = state.location.description,
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 4.dp)
                    )
                }
                item {

                    Text(
                        text = "Contact",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                    ) {
                        RoundedContactButton(
                            contentDescription = "E-mail",
                            iconId = R.drawable.ic_email
                        ) {

                        }
                        RoundedContactButton(
                            contentDescription = "Telephone",
                            iconId = R.drawable.ic_telephone
                        ) {
                            viewModel.onEvent(ContentEvent.Call("add telephone"))
                        }
                        RoundedContactButton(
                            contentDescription = "Website",
                            iconId = R.drawable.ic_computer
                        ) {}
                        RoundedContactButton(
                            contentDescription = "Location",
                            iconId = R.drawable.ic_map
                        ) {
                            viewModel.onEvent(ContentEvent.ShowOnMap(state.location.address))
                        }
                    }
                }
            }
        }
    }
}


