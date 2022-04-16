package com.geopark.feature_locations_events.domain.use_case.locations


//Wrapper class for all of the use cases

data class LocationUseCases(
    val getLocationsByTypeUseCase: GetLocationsByTypeUseCase,
    val getOrderedAndFilteredLocationsUseCase: GetOrderedAndFilteredLocationsUseCase,
    val getFilteredByQueryAndTypeLocationsUseCase: GetFilteredByQueryAndTypeLocationsUseCase,
    val getLocationByIdUseCase : GetLocationByIdUseCase
)
