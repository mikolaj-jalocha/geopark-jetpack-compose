package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import com.geopark.feature_locations_events.domain.util.LocationType
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import com.google.common.truth.Truth.assertThat

class GetLocationByIdUseCaseTest {


    private  val repository: FakeLocationRepository =  FakeLocationRepository()
    private  val getLocationById: GetLocationByIdUseCase = GetLocationByIdUseCase(repository)


    @Before
    fun setUp() {

        val locationsToInsert = mutableListOf<Location>()
        val categories = LocationType.All.toList().shuffled()

        ('a'..'e').forEachIndexed { index, c ->

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
    fun `returned location's id match passed id`() = runBlocking {
        val expectedLocationId = "c_id"

        val actualLocationId = getLocationById(expectedLocationId).location.locationId

        assertThat(actualLocationId).isEqualTo(expectedLocationId)

    }

    @Test
    fun `empty location is returned when no location was found`() = runBlocking {
        val wrongLocationId = "no_found_id"


        val actualLocationId = getLocationById(wrongLocationId).location.locationId

        assertThat(actualLocationId).isEmpty()

    }


}