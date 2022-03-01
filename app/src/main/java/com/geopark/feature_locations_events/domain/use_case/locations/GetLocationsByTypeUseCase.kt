package com.geopark.feature_locations_events.domain.use_case.locations


import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.util.LocationType
import kotlinx.coroutines.flow.map


class GetLocationsByTypeUseCase(
    private val getLocations: GetAllLocationsUseCase
) {

    operator fun invoke(
        locationType: LocationType
    ) = getFilteredLocations(locationType.toString())

    private fun getFilteredLocations(locationType: String) =
        if (locationType == LocationType.All.toString())
            getLocations()
        else
            getLocations().map { result ->
                val filteredResult = result.data.filter { location ->
                    location.categories.map { it.categoryId }.contains(locationType)
                }
                if (result is Resource.Success) {
                    return@map Resource.Success(filteredResult)
                } else
                    return@map Resource.Loading(filteredResult)
            }


}







