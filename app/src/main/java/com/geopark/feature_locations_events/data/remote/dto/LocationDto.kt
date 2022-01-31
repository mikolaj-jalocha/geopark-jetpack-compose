package com.geopark.feature_locations_events.data.remote.dto


data class LocationDto(
    val address: String,
    val categoriesIds: List<String>,
    val description: String,
    val labelsIds: List<String>,
    val latitude: String,
    val locationId: String,
    val longitude: String,
    val name: String,
    val photosIds: List<String>,
    val tagsIds: List<String>,
    val telephone: String,
    val website: String
)