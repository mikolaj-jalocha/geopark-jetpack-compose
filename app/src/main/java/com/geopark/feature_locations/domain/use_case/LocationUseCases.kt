package com.geopark.feature_locations.domain.use_case


data class LocationUseCases(
    val getLocations: GetLocations,
    val insertLocations: InsertLocations,
    val changeLocationData: ChangeLocationData
)
