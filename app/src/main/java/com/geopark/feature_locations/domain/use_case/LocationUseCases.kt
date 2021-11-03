package com.geopark.feature_locations.domain.use_case


//Wraper class for all of the usecases

data class LocationUseCases(
    val getLocations: GetLocations,
    val getLocationByName : GetLocationByName,
    val insertLocations: InsertLocations,
    val changeLocationData: ChangeLocationData
)
