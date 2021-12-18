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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geopark.R
import com.geopark.feature_events.domain.model.Event
import com.geopark.ui.theme.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

const val TILE_HEIGHT = 180
const val TILE_WIDTH = 360



@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
@Preview
fun EventListScreen() {
    val events = listOf<Event>(
        Event(
            date = "14-01-2022",
            startHour = "12:00",
            endHour = "13:30",
            photoPath = "https://media.gorykaczawskie.pl/2019/08/sudecka-zagroda-edukacyjna-4.jpg",
            title = "Zwiedzanie Sudeckiej Zagrody Edukacyjnej z przewodnikiem",
            price = "25",
            mapLocation = "Dobków 66, 59-540 Świerzawa, Poland",
            promoterName = "Sudecka Zagroda Edukacyjna",
            description = ""
    ),
        Event(
            date = "17-01-2022",
            startHour = "10:00",
            endHour = "11:15",
            photoPath = "https://media.gorykaczawskie.pl/2021/12/ice-ball-4895802-960-720.jpg",
            title = "Zimowe Labolatorium",
            price = "15",
            mapLocation = "Dobków 66, 59-540 Świerzawa, Poland",
            promoterName = "Sudecka Zagroda Edukacyjna",
            description = ""
        ),
        Event(
            date = "12-01-2022",
            startHour = "14:00",
            endHour = "15:30",
            photoPath = "https://media.gorykaczawskie.pl/2021/12/dsc-0339.jpg",
            title = "Samuraje i Sushi",
            price = "20",
            mapLocation = "Dobków 66, 59-540 Świerzawa, Poland",
            promoterName = "Sudecka Zagroda Edukacyjna",
            description = ""
        ))

    Scaffold {
        Column {
            EventTile(event = events[0])
            EventTile(event = events[1])
            EventTile(event = events[2])
        }
    }

}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun EventTile(modifier: Modifier = Modifier, event: Event) {

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
            event.apply {
                if (isReversed.value) {
                    EventTileInfoContent(title,date, startHour,endHour,price,promoterName,mapLocation)
                } else {
                    EventTilePhotoContent(title,photoPath)
                }
            }
            Row(Modifier.align(TopEnd)) {
                IconButton(onClick = onFavoriteClick,) {
                    Icon(
                        painterResource(id = if (isFavorite.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline),
                        contentDescription = "Click to add event to favorites",
                        tint = if(!isReversed.value) Color.White else NavyBlue
                    )
                }
                IconButton(
                    onClick = onReverseChange
                ) {
                    if (!isReversed.value)
                        Icon(Icons.Outlined.Info, "Click to see short info about event", tint = Color.White)
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


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
private fun EventTilePhotoContent(title: String, photoPath : String) {

    Box {
        Image(
            painter = rememberImagePainter(data = photoPath),
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
    title : String,
    date : String,
    startHour : String,
    endHour : String,
    price : String,
    promoterName : String,
    mapLocation: String
    ) {

    val dateCounter = (date+startHour).getDate()


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
                        text = dateCounter,
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
                text = promoterName,
                style = MaterialTheme.typography.subtitle1,
                color = DenimBlue,
                modifier = Modifier.align(CenterHorizontally)
            )
            Text(
                text = mapLocation,
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

// TODO: Change this function name so it's no longer confusing
fun String.getDate() : String{

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyyHH:mm")

    val toDate= LocalDateTime.parse(this,formatter)
    val fromNow = LocalDateTime.now()

    var temp = LocalDateTime.from(fromNow)

    val days = temp.until(toDate, ChronoUnit.DAYS)
    temp =  temp.plusDays(days)

    val hours = temp.until(toDate, ChronoUnit.HOURS)

    temp = temp.plusHours(hours)
    val minutes = temp.until(toDate, ChronoUnit.MINUTES)

    val day  = if(days > 0) "d" else ""
    val hour = if (hours > 0) "h" else ""
    val minute = "m"

    return "$days$day $hours$hour $minutes$minute"

}


