package com.geopark.feature_locations_events.data.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.data.source.dao.FakeLocationDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class LocationRepositoryImplTest {

    private val dao = FakeLocationDao()
    private val locationRepository = LocationRepositoryImpl(dao)


    @Test
    fun `returned location match passed id`() = runBlocking {
        insertData()
        val expectedLocationId = "location_1"
        val actualLocationId  : String = locationRepository.getLocationById("location_1")!!.location.locationId

        assertThat(actualLocationId).isEqualTo(expectedLocationId)
        //assert(location!!.location.locationId == "location_1")
    }

    @Test
    fun `when result is empty Resource Loading is returned`() = runBlocking {
        val resource = locationRepository.getLocationsFlow().first()

        assertThat(resource.data).isEmpty()
        assertThat(resource is Resource.Loading).isTrue()
    }


    @Test
    fun `when result is not empty Resource Success is returned`() = runBlocking {
        insertData()
        val resource = locationRepository.getLocationsFlow().first()
        assertThat(resource.data).isNotEmpty()
        assertThat(resource is Resource.Success).isTrue()
    }


    private fun insertData() {
        val data = mutableListOf(
            Location(
                location = LocationEntity(locationId = "location_1", name = "location_1_name"),
                categories = listOf(
                    CategoryEntity(categoryId = "gastronomia", ""),
                    CategoryEntity(categoryId = "noclegi", "")
                )
            ),
            Location(
                location = LocationEntity(locationId = "location_2", name = "location_2_name"),
                categories = listOf(
                    CategoryEntity(categoryId = "sport", "")
                )
            ),
            Location(
                location = LocationEntity(locationId = "location_3", name = "location_3_name"),
                categories = listOf(
                    CategoryEntity(categoryId = "gastronomia", "")
                )
            )
        )
        dao.insertLocations(data)
    }

}