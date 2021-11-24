package com.geopark.feature_locations.presentation.content

sealed class ContentEvent {
    data class ShowOnMap (val data : String) : ContentEvent()
    data class Call (val data : String) : ContentEvent()
}