package com.geopark.feature_locations.data.repository

import com.geopark.feature_locations.data.data_source.LocationDao
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository
import kotlinx.coroutines.flow.*

class LocationRepositoryImpl(
    private val dao: LocationDao
) : LocationRepository {

    override suspend  fun updateLocation(location: Location) {
        dao.updateLocation(location)
    }

    override fun getLocations(): Flow<List<Location>> {
        return dao.getLocations()
    }


    override suspend fun getLocationByName(name: String): Location? {
        return dao.getLocationByName(name)
    }

    override suspend fun insertLocation(location: Location) {
        dao.insertLocation(location)
    }

    override suspend fun deleteLocation(location: Location) {
        dao.deleteLocation(location)
    }

}