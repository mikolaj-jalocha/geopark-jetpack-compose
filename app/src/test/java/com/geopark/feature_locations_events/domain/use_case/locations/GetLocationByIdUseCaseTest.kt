package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLocationByIdUseCaseTest {


    private  val repository: FakeLocationRepository =  FakeLocationRepository()
    private  val getLocationById: GetLocationByIdUseCase = GetLocationByIdUseCase(repository)



    @Test
    fun `returned location's id match passed id`() = runBlocking {
        val locationId = "location_2"
        assert(getLocationById(locationId).location.locationId == locationId)

    }

    @Test
    fun `empty location is returned when no location was found`() = runBlocking {
        val locationId = "no_found_id"
        assert(getLocationById(locationId).location.locationId.isEmpty())
    }


}