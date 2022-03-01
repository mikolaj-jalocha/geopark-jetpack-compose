package com.geopark.feature_locations_events.domain.use_case.locations


//Wrapper class for all of the use cases

data class LocationUseCases(
    val getAllLocationsUseCase : GetAllLocationsUseCase,
    val getLocationsByTypeUseCase: GetLocationsByTypeUseCase,
    val getOrderedLocationsUseCase: GetOrderedLocationsUseCase,
    val getFilteredLocationsUseCase: GetFilteredLocationsUseCase
)
