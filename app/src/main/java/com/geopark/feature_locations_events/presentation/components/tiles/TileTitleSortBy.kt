package com.geopark.feature_locations_events.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geopark.feature_locations_events.domain.util.OrderType
import com.geopark.ui.theme.BabyBlue


@ExperimentalMaterialApi
@Composable

fun TileTitleSortBy(modifier : Modifier = Modifier, title : String , onSortClick : (OrderType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
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

