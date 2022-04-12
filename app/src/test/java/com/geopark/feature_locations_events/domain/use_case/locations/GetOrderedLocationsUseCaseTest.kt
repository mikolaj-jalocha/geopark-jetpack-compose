package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetOrderedLocationsUseCaseTest {


    private val repository: FakeLocationRepository = FakeLocationRepository()
    private val getAllLocations = GetAllLocationsUseCase(repository)
    private val getLocationByType = GetLocationsByTypeUseCase(getAllLocations)
    private val getOrderedLocations = GetOrderedLocationsUseCase(getLocationByType)


    private val sortTypes = listOf(
        SortType.Descending,
        SortType.Ascending,
        SortType.Default
    )

    @Test
    fun `returned locations are in ascending order`() = runBlocking {

        val actualLocationsOrder = getOrderedLocations(
            sortType = SortType.Ascending,
            LocationType.All
        ).first().data.map { it.location.name }

        val expectedLocationsOrder = repository.data.map { it.location.name }.sorted()

        assert(actualLocationsOrder.size == expectedLocationsOrder.size)


        actualLocationsOrder.forEachIndexed { index, actualLocation ->
            assert(actualLocation == expectedLocationsOrder[index])
        }
    }


    @Test
    fun `when default sort type is passed, returned locations are in ascending order`() = runBlocking {

        val actualLocationsOrder = getOrderedLocations(
            sortType = SortType.Default,
            LocationType.All
        ).first().data.map { it.location.name }

        val expectedLocationsOrder = repository.data.map { it.location.name }.sorted()

        assert(actualLocationsOrder.size == expectedLocationsOrder.size)


        actualLocationsOrder.forEachIndexed { index, actualLocation ->
            assert(actualLocation == expectedLocationsOrder[index])
        }
    }

    @Test
    fun `returned locations are in descending order`() = runBlocking {

        val actualLocationsOrder = getOrderedLocations(
            sortType = SortType.Descending,
            LocationType.All
        ).first().data.map { it.location.name }

        val expectedLocationsOrder = repository.data.map { it.location.name }.sortedDescending()

        assert(actualLocationsOrder.size == expectedLocationsOrder.size)


        actualLocationsOrder.forEachIndexed { index, actualLocation ->
            assert(actualLocation == expectedLocationsOrder[index])
        }

    }
    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getOrderedLocations(SortType.Default, locationType = LocationType.All).first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource = getOrderedLocations(SortType.Default, locationType = LocationType.All).first()


        assert(successResource is Resource.Success)

    }

}