package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import com.geopark.feature_locations_events.domain.util.LocationType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFilteredLocationsUseCaseTest {


    private val repository: FakeLocationRepository = FakeLocationRepository()
    private val getAllLocations = GetAllLocationsUseCase(repository)
    private val getLocationByType = GetLocationsByTypeUseCase(getAllLocations)
    private val getOrderedLocations = GetOrderedLocationsUseCase(getLocationByType)
    private val getFilteredLocations =
        GetFilteredLocationsUseCase(getLocationByType, getOrderedLocations)


    @Test
    fun `returned locations contains query in their name`() = runBlocking {

        val searchQueries = listOf("location", "name", "_", "2")

        searchQueries.forEach { searchQuery ->

            val actualLocationsName = getFilteredLocations(
                locationType = LocationType.All,
                searchQuery = searchQuery
            ).first().data.map { it.location.name }

            assert(actualLocationsName.isNotEmpty())

            actualLocationsName.forEach { locationName ->
                assert(locationName.contains(searchQuery))
            }
        }

    }

    @Test
    fun `when passed exact name of location returns exactly matched locations`() = runBlocking {
        val searchQuery = "location_2_name"

        val actualLocations = getFilteredLocations(
            locationType = LocationType.All,
            searchQuery = searchQuery
        ).first().data

        actualLocations.forEach { location ->
            assert(location.location.name == searchQuery)
        }
    }

    @Test
    fun `when no location matches passed query, empty list is returned`() = runBlocking {

        val searchQuery = "query_that_does_not_match"

        val actualLocationsSize = getFilteredLocations(
            locationType = LocationType.All,
            searchQuery = searchQuery
        ).first().data.size

        assert(actualLocationsSize == 0)
    }


    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getFilteredLocations("", locationType = LocationType.All).first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource = getFilteredLocations("", locationType = LocationType.All).first()

        assert(successResource is Resource.Success)

    }


}