package com.geoparkcompose.ui.components.components

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
import com.geoparkcompose.R
import com.geoparkcompose.ui.theme.BabyBlue
import com.google.accompanist.insets.systemBarsPadding


@ExperimentalMaterialApi
@Composable
fun CardContentBody(
    title: String,
    description: String,
    location: String ,
    onNavigationClick: () -> Unit
) {
    Column(
        modifier =
        Modifier.verticalScroll(rememberScrollState())
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.ostrzyca),
                contentDescription = "Main photography",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            CardContentNavigationBar(onNavigationClick)
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
                text = title,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp, top = 16.dp)
            )
            Text(
                text = location,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(0.3f)
                    .padding(start = 20.dp)
            )


            Button(
                onClick = { },
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
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 20.dp, top = 16.dp)
            )

            // TODO: 19.09.2021 Adjust better color for text
            Text(
                text = description,
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
                ContactItem(title = "E-mail", iconId = R.drawable.ic_email) {}
                ContactItem(
                    title = "Telephone",
                    iconId = R.drawable.ic_telephone
                ) {}
                ContactItem(title = "Website", iconId = R.drawable.ic_computer) {}
                ContactItem(title = "Location", iconId = R.drawable.ic_map) {}
            }

        }
    }
}


@ExperimentalMaterialApi
@Composable
fun CardContentNavigationBar(onNavigationClick: () -> Unit) {
    Row(
        modifier = Modifier
            .systemBarsPadding()
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundedIcon(iconId = R.drawable.ic_arrow_back, onClick = { onNavigationClick() })
        RoundedIcon(iconId = R.drawable.ic_bookmark_outline, iconSize = 25.dp, onClick = {})
    }
}
