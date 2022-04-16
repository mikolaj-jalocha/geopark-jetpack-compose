package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetOrderedAndFilteredLocationsUseCase(
    private val getFilteredLocations: GetFilteredByQueryAndTypeLocationsUseCase,

) {

    operator fun invoke(
        sortType: SortType,
        locationType: LocationType,
        searchQuery  : String
    ) =
        orderLocations(getFilteredLocations(searchQuery,locationType), sortType)

    private fun orderLocations(data: Flow<Resource<List<Location>>>, sortType: SortType) =
        data.map { result ->
            val orderedResult = if (sortType == SortType.Descending)
                result.data.sortedByDescending {
                    it.location.name
                }
            else
                result.data.sortedBy { it.location.name }

            if (result is Resource.Success)
                return@map Resource.Success(orderedResult)
            else
                return@map Resource.Loading(orderedResult)


        }
}
