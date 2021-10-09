package com.geoparkcompose.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geoparkcompose.R
import com.geoparkcompose.data.CardData
import com.geoparkcompose.ui.theme.BabyBlue
import com.geoparkcompose.ui.theme.CinnabarRed
import com.geoparkcompose.ui.theme.GeoparkTheme
import com.geoparkcompose.utils.CategoryItem
import com.geoparkcompose.utils.DamiContentItem


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CardsSection(
    title: String,
    items: List<CardData>,
    isWide: Boolean = false,
    isHorizontal: Boolean = false,
    onItemClick : (CardData) -> Unit,
    onSeeAllClick : (String) -> Unit = {}
) {
    Column {

        if (isWide)
            ColumnTitleLong(title = title) {
                // OnClick actions here
            }
        else
            ColumnTitleShort(title = title) {
                onSeeAllClick(it)
            }
        if (isHorizontal) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                itemsIndexed(items) { _, item ->
                    ItemCard(backgroundId = R.drawable.gosciniec, name = item.name, isWide = isWide){
                                onItemClick(item)
                    }
                }
            }
        } else {
            LazyRow {
                itemsIndexed(items) { _, item ->
                    ItemCard(backgroundId = R.drawable.kosciol, name = item.name, isWide = isWide){
                        onItemClick(item)
                    }
                }
            }
        }
    }

}

@Composable
fun ColumnTitleLong(title: String, onClick: () -> Unit) {

    Column(Modifier.padding(start = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Result found (128)",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(0.3f))


            // TODO: Add sorting mechanism
            Row() {
                Text(text = "Sort By",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Justify)
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp, end = 16.dp)
                )
            }

        }
    }
}

@Composable
fun ColumnTitleShort(title: String, seeAllClick: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 17.dp)
        .fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.h6,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = BabyBlue,
            modifier = Modifier
                .clickable(onClick = {seeAllClick(title)})
                .align(Alignment.CenterVertically)
        )
    }

}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ItemCard(backgroundId: Int, name: String, isWide: Boolean, onClick: () -> Unit) {
        Card(shape = RoundedCornerShape(26.dp), modifier = Modifier
            .padding(start = 16.dp)
            .width(if (isWide) 360.dp else 180.dp)
            .height(200.dp), onClick = onClick) {
            Box {
                Image(
                    painter = rememberImagePainter(backgroundId),
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                val isRed = remember { mutableStateOf(value = false) }

                val animatedColor =
                    animateColorAsState(if (isRed.value) CinnabarRed else Color.Black)

                IconButton(onClick = { isRed.value = !isRed.value },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(Color.White)) {
                    Icon(painter = painterResource(if (isRed.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline),
                        tint = animatedColor.value,
                        contentDescription = "Bookmark")
                }

                // TODO: 10.09.2021 Extract to separate function for later reuse
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
                                if (isWide) 400f else 360f
                            )
                        )) {

                }

                Column(modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)) {
                    Text(text = name,
                        color = Color.White,
                        fontSize = if (isWide) 25.sp else 21.sp,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.SemiBold)
                }
            }


        }
}
