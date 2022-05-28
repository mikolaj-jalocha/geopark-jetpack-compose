package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import com.geopark.feature_locations_events.domain.util.LocationType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import com.google.common.truth.Truth.assertThat

class GetLocationsByTypeUseCaseTest {


    private val repository: FakeLocationRepository = FakeLocationRepository()
    private val getLocationByType = GetLocationsByTypeUseCase(GetAllLocationsUseCase(repository))
    private val locationTypes = listOf(
        LocationType.Active,
        LocationType.Explore,
        LocationType.Hotel,
        LocationType.Restaurant
    )


    @Before
    fun setUp() {

        val locationsToInsert = mutableListOf<Location>()
        val categories = LocationType.All.toList().shuffled()

        ('a'..'z').forEachIndexed { index, c ->

            locationsToInsert.add(
                Location(
                    location = LocationEntity(
                        locationId = c.toString() + "_id",
                        name = c.toString() + "_name"
                    ),
                    categories = listOf(
                        CategoryEntity(categoryId = categories[Random.nextInt(0, 3)], ""),
                        CategoryEntity(categoryId = categories[Random.nextInt(4, 5)], "")
                    )
                )
            )
        }
        locationsToInsert.shuffle()
        locationsToInsert.forEach {
            repository.insertLocation(it)
        }

    }

    @Test
    fun `returned locations match given type`() = runBlocking {

        locationTypes.forEach { expectedType ->

            val filteredLocations = getLocationByType(expectedType).first().data

            filteredLocations.forEach { location ->
                val actualType = location.categories.map { it.categoryId }

                assertThat(actualType).contains(expectedType.toString())
            }

        }
    }

    @Test
    fun `all locations are returned if passed type is ALL`() = runBlocking {

        val expectedLocationsSize = repository.getLocationsSize()
        val actualLocationsSize = getLocationByType(LocationType.All).first().data.size

        assertThat(expectedLocationsSize).isEqualTo(actualLocationsSize)
    }


    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getLocationByType(LocationType.All).first()

        assertThat(actualReturnType is Resource.Loading).isTrue()
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val actualReturnType = getLocationByType(LocationType.All).first()

        assertThat(actualReturnType is Resource.Success).isTrue()

    }


}