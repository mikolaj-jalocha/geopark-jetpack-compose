package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.SortType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class GetOrderedAndFilteredLocationsUseCaseTest {

    private val repository: FakeLocationRepository = FakeLocationRepository()
    private val getOrderedLocations = GetOrderedAndFilteredLocationsUseCase(
        GetFilteredByQueryAndTypeLocationsUseCase(
            GetLocationsByTypeUseCase(
                GetAllLocationsUseCase(
                    repository
                )
            )
        )
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
    fun `returned locations size match original locations size`() = runBlocking {

        val orderedLocations = getOrderedLocations(
            sortType = SortType.Ascending,
            LocationType.All,
            ""
        ).first().data

        assertThat(orderedLocations.size).isEqualTo(repository.getLocationsSize())
    }


    @Test
    fun `returned locations are in ascending order`() = runBlocking {

        val orderedLocations = getOrderedLocations(
            sortType = SortType.Ascending,
            LocationType.All,
            ""
        ).first().data.map { it.location.name }


        for (i in 0..orderedLocations.size - 2) {
            assertThat(orderedLocations[i]).isLessThan(orderedLocations[i + 1])
        }

    }

    @Test
    fun `returned locations are in descending order`() = runBlocking {

        val orderedLocations = getOrderedLocations(
            sortType = SortType.Descending,
            LocationType.All,
            ""
        ).first().data.map { it.location.name }

        for (i in 0..orderedLocations.size - 2) {
            assertThat(orderedLocations[i]).isGreaterThan(orderedLocations[i + 1])
        }

    }


    @Test
    fun `when default sort type is passed, returned locations are in ascending order`() =
        runBlocking {

            val orderedLocations = getOrderedLocations(
                sortType = SortType.Default,
                LocationType.All,
                ""
            ).first().data.map { it.location.name }


            for (i in 0..orderedLocations.size - 2) {
                assertThat(orderedLocations[i]).isLessThan(orderedLocations[i + 1])
            }

        }

    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType =
            getOrderedLocations(SortType.Default, locationType = LocationType.All, "").first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource =
            getOrderedLocations(SortType.Default, locationType = LocationType.All, "").first()


        assert(successResource is Resource.Success)

    }


}