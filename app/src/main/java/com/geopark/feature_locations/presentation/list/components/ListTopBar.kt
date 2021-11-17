package com.geopark.feature_locations.presentation.list.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.geopark.R
import com.geopark.feature_locations.presentation.components.RoundedIcon
import com.google.accompanist.insets.systemBarsPadding


// TODO: Hoist state up
//  Reason: Search bar state should be hoisted up because when user wants to lose focus of it, often clicks in other area
// TODO: Add suggestions to typed query https://material.io/design/navigation/search.html#persistent-search

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
@Preview
fun ListTopBar(onClick: () -> Unit = {}, onValueChange: (String) -> Unit = {}) {

    Row(
        Modifier
            .systemBarsPadding(bottom = false)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedIcon(
            modifier = Modifier.padding(16.dp),
            iconId = R.drawable.ic_arrow_back
        ) { onClick() }

        var text by rememberSaveable { mutableStateOf("") }

        var isSearchEnabled by rememberSaveable {
            mutableStateOf(false)
        }

        val durationSlow = 700
        val durationFast = 300

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    isSearchEnabled = !isSearchEnabled
                },
            ) {
                Icon(Icons.Default.Search, "search")
            }

            AnimatedVisibility(
                visible = isSearchEnabled,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = durationFast)
                ) + expandHorizontally(
                    expandFrom = Alignment.End,
                    animationSpec = tween(
                        durationMillis = durationSlow,
                        easing = FastOutLinearInEasing,
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = durationSlow,
                        easing = FastOutLinearInEasing,
                    )
                ) + shrinkHorizontally(
                    shrinkTowards = Alignment.End,
                    animationSpec = tween(
                        durationMillis = durationSlow,
                        easing = FastOutLinearInEasing,
                    )
                )
            ) {
                OutlinedTextField(

                    modifier = Modifier.padding(end = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    value = text,
                    placeholder = { Text("Search") },
                    onValueChange = {
                        text = it; onValueChange(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (text.isEmpty())
                                isSearchEnabled = false
                            else
                                text = ""; onValueChange("")
                        }) {
                            Icon(
                                Icons.Outlined.Clear,
                                tint = MaterialTheme.colors.onSurface,
                                contentDescription = "Clear search field"
                            )
                        }
                    }
                )
            }

            Image(
                rememberImagePainter(R.mipmap.ic_geopark_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp)
            )
        }


    }
}

