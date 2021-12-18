package com.geopark.feature_events.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geopark.R
import com.geopark.ui.theme.*

const val TILE_HEIGHT = 180
const val TILE_WIDTH = 360

data class locationEvent(
    val name: String,
    val startHour: String,
    val endHour: String,
    val placeName: String,
    val placeLocation: String,
    val duration: String,
    val price: String,
)

@Composable
//@Preview
fun EventListScreen() {

    val loc = listOf<locationEvent>(
        locationEvent(
            "Cztery żywioły: ogień",
            "11:30",
            "13:15",
            "Sudecka Zagroda Edukacyjna",
            "Dobków 66, 59-540 Świerzawa",
            "1h 45m",
            "25"
        ),
        locationEvent(
            "Zwiedzanie Sudeckiej Zagrody Edukacyjnej z przewodnikiem",
            "12:00",
            "13:30",
            "Sudecka Zagroda Edukacyjna",
            "Dobków 66, 59-540 Świerzawa",
            "1h 30m",
            "25"
        ),
        locationEvent(
            "Samuraje i sushi",
            "14:00",
            "16:00",
            "Sudecka Zagroda Edukacyjna",
            "Dobków 66, 59-540 Świerzawa",
            "1h 00m",
            "25"
        ),
    )

}

@ExperimentalMaterialApi
@Composable
@Preview
fun EventTile(modifier: Modifier = Modifier) {

    val isReversed = remember { mutableStateOf(value = false) }
    val isFavorite = remember { mutableStateOf(value = false) }
    val onReverseChange: () -> Unit = { isReversed.value = !isReversed.value }
    val onFavoriteClick: () -> Unit = { isFavorite.value = !isFavorite.value }

    Card(
        shape = RoundedCornerShape(26.dp),
        elevation = 4.dp,
        modifier = modifier
            .width(TILE_WIDTH.dp)
            .height(TILE_HEIGHT.dp)
    ) {
        Box {
            if (isReversed.value) {
                EventTileInfoContent()
            } else {
                EventTilePhotoContent()
            }
            Row(Modifier.align(TopEnd)) {
                IconButton(onClick = onFavoriteClick,) {
                    Icon(
                        painterResource(id = if (isFavorite.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline),
                        contentDescription = "Click to add event to favorites"
                    )
                }
                IconButton(
                    onClick = onReverseChange
                ) {
                    if (!isReversed.value)
                        Icon(Icons.Outlined.Info, "Click to see short info about event ")
                    else
                        Icon(
                            painterResource(id = R.drawable.ic_outline_image),
                            "click to see event's image",
                            tint = NavyBlue
                        )
                }

            }
        }
    }

}


@ExperimentalMaterialApi
@Composable
private fun EventTilePhotoContent(title: String = "Cztery żywioły: ogień") {

    Box() {
        Image(
            painter = painterResource(id = R.drawable.ogien),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = "Image of an event",
            contentScale = ContentScale.Crop,
        )
        //Gradient
        Column(
            Modifier
                .align(Alignment.BottomStart)
                .height(125.dp)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black),
                        1f,
                        365f
                    )
                )
        ) {}

        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .align(BottomStart)
                .padding(8.dp, bottom = 12.dp)
        )
    }
}


@Composable
private fun EventTileInfoContent(
    title: String = "Cztery żywioły: ogień",
    startHour: String = "11:30",
    endHour: String = "13:15",
    placeName: String = "Sudecka Zagroda Edukacyjna",
    placeLocation: String = "Dobków 66, 59-540 Świerzawa, Poland",
    duration: String = "1h 45m",
    price: String = "25",
    date: String = "21-01-2022",

    ) {
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    color = CinnabarRed,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = date,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.align(CenterHorizontally),
                color = NavyBlue
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = startHour,
                        style = MaterialTheme.typography.body2,
                        color = NavyBlue
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.caption,
                        color = AmericanoGray
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .size(16.dp),
                        painter = painterResource(R.drawable.ic_fire),
                        contentDescription = "Event type icon"
                    )
                    Text(
                        text = "Starts in:",
                        style = MaterialTheme.typography.overline,
                        color = AmericanoGray
                    )
                    Text(
                        text = duration,
                        style = MaterialTheme.typography.caption,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Red57,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = endHour,
                        style = MaterialTheme.typography.body2,
                        color = NavyBlue
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = "End",
                        style = MaterialTheme.typography.caption,
                        color = AmericanoGray
                    )
                }
            }
        }

        Row {
            Text(
                "$$price",
                style = MaterialTheme.typography.body2,
                color = NavyBlue
            )
            Text(
                "/person",
                style = MaterialTheme.typography.caption,
                color = AmericanoGray,
                fontSize = 10.sp,
                modifier = Modifier.align(CenterVertically)
            )
        }

        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = placeName,
                style = MaterialTheme.typography.subtitle1,
                color = DenimBlue,
                modifier = Modifier.align(CenterHorizontally)
            )
            Text(
                text = placeLocation,
                style = MaterialTheme.typography.caption,
                fontSize = 10.sp,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(bottom = 4.dp),
                color = AmericanoGray
            )
        }
    }
}


