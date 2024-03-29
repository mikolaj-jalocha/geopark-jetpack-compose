package com.geoparkcompose.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.geoparkcompose.R
import com.geoparkcompose.ui.theme.CinnabarRed
import com.google.accompanist.insets.systemBarsPadding


@ExperimentalMaterialApi
@Composable
fun MainTopBar(title: String = "Geopark App") {
    Column(Modifier.systemBarsPadding(bottom = false)) {
        TitleBar(title)
        Spacer(modifier = Modifier.size(5.dp))
        TabBar()
    }
}

@Composable

fun TitleBar(title: String = "Geopark App") {
    Row(modifier = Modifier
        .padding(top = 5.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = title,
            style = MaterialTheme.typography.h4,
            fontSize = 25.sp,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = 16.dp))

        Image(painter = rememberImagePainter(data = R.mipmap.ic_geopark_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(60.dp))
    }

}

@Composable
fun TabBar(
    initialSelectedItemIndex: Int = 0,
    titles: List<String> = listOf("Odkrywaj",
        "Paszport Odkrywcy",
        "Wydarzenia",
        "Informacje",
        "Kontakt"),
) {

    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        AnimatedTabRowIndicator(tabPositions, selectedItemIndex)
    }

    ScrollableTabRow(
        backgroundColor = Color.White,
        edgePadding = 4.dp,
        indicator = indicator,
        selectedTabIndex = selectedItemIndex,

        ) {
        titles.forEachIndexed { index, item ->
            TitleTabItem(item,
                { selectedItemIndex = index },
                selected = index == selectedItemIndex)

        }
    }

}


@Composable
fun TitleTabItem(title: String = "Hotele", onClick: () -> Unit = {}, selected: Boolean = false) {

    // TODO: UI: Change shadow style after click

    Tab(selected, onClick, modifier = Modifier
        .background(Color.Transparent)
        .clip(RoundedCornerShape(15.dp))) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                color = if (selected) CinnabarRed else Color.Gray,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.Center),
                textAlign = TextAlign.Justify)
        }
    }

}

@Composable
fun AnimatedTabRowIndicator(tabPositions: List<TabPosition>, selectedTabIndex: Int) {

    //Indicator animations
    val transition = updateTransition(selectedTabIndex)
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            spring(dampingRatio = 1f, stiffness = Spring.StiffnessLow)
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(transitionSpec = {
        spring(dampingRatio = 1f, stiffness = Spring.StiffnessLow)
    }, label = ""
    ) {
        tabPositions[it].right
    }

    // Actual Indicator
    Divider(thickness = 2.dp, color = CinnabarRed, modifier = Modifier
        .padding(bottom = 2.dp)
        .wrapContentSize(align = Alignment.BottomStart)
        .offset(x = (indicatorStart + indicatorEnd) / 2 - 8.dp)
        .width(14.dp)
        .clip(CircleShape))


}


