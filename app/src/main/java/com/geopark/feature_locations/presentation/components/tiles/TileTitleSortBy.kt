package com.geopark.feature_locations.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.domain.util.OrderType
import com.geopark.ui.theme.BabyBlue


@ExperimentalMaterialApi
@Composable

fun TileTitleSortBy(modifier : Modifier = Modifier, title : String , onSortClick : (OrderType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
            //.padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 17.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = title,
            style = MaterialTheme.typography.h6,
        )

        Row (modifier = Modifier.clickable { expanded = true }){
                Row (horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = "Sort by",
                        style = MaterialTheme.typography.h6,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BabyBlue,
                    )
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(onClick = { onSortClick(OrderType.Ascending)}) {
                        Text("Ascending")

                    }
                    DropdownMenuItem(onClick = {  onSortClick(OrderType.Descending)}) {
                        Text("Descending")
                    }
                    DropdownMenuItem(onClick = { onSortClick(OrderType.CertificatedFirst)}) {
                        Text("Certificated First")
                    }
                }

        }
    }
}

