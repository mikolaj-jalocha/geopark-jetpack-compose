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
import com.geopark.core.util.Constants.SMALL_TILE_SIZE
import com.geopark.core.util.Constants.WIDE_TILE_SIZE
import com.geopark.ui.theme.CinnabarRed

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun Tile(
    modifier: Modifier = Modifier,
    photoPath: String,
    name: String,
    isWide: Boolean,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(26.dp), modifier = modifier
            .width(if (isWide) WIDE_TILE_SIZE.dp else SMALL_TILE_SIZE.dp)
            .height(200.dp)
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

            IconButton(onClick = {
                isRed.value = !isRed.value
                onFavoriteClick()
            },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(colors.surface)) {
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
