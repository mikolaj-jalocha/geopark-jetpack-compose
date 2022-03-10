package com.geopark.feature_locations_events.presentation.menu.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geopark.R
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp







enum class  TileSizes(val width : Dp, val height : Dp){
    SMALL_NORMAL(180.dp,200.dp),
    SMALL_SMALL(170.dp,190.dp),
    WIDE_NORMAL(360.dp,200.dp),
    WIDE_SMALL(340.dp,190.dp)
}


@ExperimentalCoilApi
@Composable
fun Tile(
    modifier: Modifier = Modifier,
    photoPath: String,
    name: String,
    isWide: Boolean,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {


    val config = LocalConfiguration.current
     val dimensions = when{
        config.screenWidthDp < 380 && isWide -> TileSizes.WIDE_SMALL
        config.screenWidthDp > 380 && isWide -> TileSizes.WIDE_NORMAL
        config.screenWidthDp < 380 && !isWide -> TileSizes.SMALL_SMALL
        config.screenWidthDp > 380 && !isWide -> TileSizes.SMALL_NORMAL
        else -> {
            TileSizes.SMALL_NORMAL
        }
    }

    Card(
        shape = RoundedCornerShape(26.dp), modifier = modifier
            .width(dimensions.width)
            .height(dimensions.height)
    ) {
        Box {
            Image(
                painter = rememberImagePainter(data = photoPath),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            val isRed = remember { mutableStateOf(value = isFavorite) }

            val animatedColor =
                animateColorAsState(if (isRed.value) colors.primary else colors.onSurface)

            IconButton(
                onClick = {
                    isRed.value = !isRed.value
                    onFavoriteClick()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(colors.surface)
            ) {
                Icon(
                    painter = painterResource(if (isRed.value) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline),
                    tint = animatedColor.value,
                    contentDescription = "Bookmark"
                )
            }


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
                    )
            ) {

            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = name,
                    color = colors.surface,
                    fontSize = if (isWide) 25.sp else 21.sp,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }


    }
}
