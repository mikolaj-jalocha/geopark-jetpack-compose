package com.geopark.feature_locations.presentation.content.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geopark.R
import com.geopark.feature_locations.presentation.content.ContentViewModel
import com.geopark.ui.theme.BabyBlue


@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun ContentScreen(
    viewModel : ContentViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Scaffold(
        // TODO app topbar
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box() {
                Image(
                    painter = rememberImagePainter(data = state.photo),
                    contentDescription = "Main photography",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

            }
            Column(
                Modifier
                    .offset(y = (-50).dp)
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(Color.White),
            ) {
                Text(
                    text = state.name,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                )
                Text(
                    text = state.location,
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
                        .align(Alignment.End)
                        .padding(end = 20.dp)
                ) {
                    Text(
                        text = "Navigate",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = state.name,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                )

                // TODO: 19.09.2021 Adjust better color for text
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 4.dp)
                )


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
                    RoundedIconButton(title = "E-mail", iconId = R.drawable.ic_email) {}
                    RoundedIconButton(
                        title = "Telephone",
                        iconId = R.drawable.ic_telephone
                    ) {}
                    RoundedIconButton(title = "Website", iconId = R.drawable.ic_computer) {}
                    RoundedIconButton(title = "Location", iconId = R.drawable.ic_map) {}
                }

            }
        }
    }
    }

