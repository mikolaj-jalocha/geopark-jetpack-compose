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


class GetFilteredByQueryAndTypeLocationsUseCaseTest {


    private val repository: FakeLocationRepository = FakeLocationRepository()
    private val getAllLocations = GetAllLocationsUseCase(repository)
    private val getLocationByType = GetLocationsByTypeUseCase(getAllLocations)
    private val getFilteredLocations =
        GetFilteredByQueryAndTypeLocationsUseCase(getLocationByType)


    @Before
    fun setUp() {

        val locationsToInsert = mutableListOf<Location>()
        val categories = LocationType.All.toList().shuffled()

        ('a'..'z').forEachIndexed { index, c ->

            locationsToInsert.add(
                Location(
                    location = LocationEntity(
                        locationId = c.toString() +"_id",
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
    fun `returned locations contains query in their name`() = runBlocking {

        val searchQueries = listOf( "name", "_", "n")

        searchQueries.forEach { searchQuery ->

            val actualLocationsName = getFilteredLocations(
                locationType = LocationType.All,
                searchQuery = searchQuery
            ).first().data.map { it.location.name }

            assertThat(actualLocationsName).isNotEmpty()

            actualLocationsName.forEach { locationName ->
                assertThat(locationName).contains(searchQuery)
            }
        }

    }

    @Test
    fun `when passed exact name of location returns exactly matched locations`() = runBlocking {
        val searchQuery = "a_name"

        val actualLocations = getFilteredLocations(
            locationType = LocationType.All,
            searchQuery = searchQuery
        ).first().data

        actualLocations.forEach { location ->
            assertThat(location.location.name).isEqualTo(searchQuery)
        }
    }

    @Test
    fun `when no location matches passed query, empty list is returned`() = runBlocking {

        val searchQuery = "query_that_does_not_match"

        val actualLocationsSize = getFilteredLocations(
            locationType = LocationType.All,
            searchQuery = searchQuery
        ).first().data.size

        assertThat(actualLocationsSize).isEqualTo(0)
    }


    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getFilteredLocations("", locationType = LocationType.All).first()

        assertThat(actualReturnType is Resource.Loading).isTrue()
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val actualReturnType = getFilteredLocations("", locationType = LocationType.All).first()

        assertThat(actualReturnType is Resource.Success).isTrue()

    }


}