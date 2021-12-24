package com.geopark.feature_locations_events.domain.model

data class Event(
    val date : String,
    val startHour : String,
    val endHour : String,
    val photoPath : String,
    val title : String,
    val price  : String,
    val mapLocation : String,
    val promoterName : String,
    val description : String
)
