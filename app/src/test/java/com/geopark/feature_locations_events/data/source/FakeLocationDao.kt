package com.geopark.feature_locations_events.data.source

import androidx.annotation.VisibleForTesting
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationCategoryCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationLabelCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationPhotoCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationTagCrossRef
import com.geopark.feature_locations_events.data.local.dao.LocationDao
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocationDao : LocationDao {



    fun insertLocations(locations : List<Location>){
            data.clear()
            data.addAll(locations)
    }


    private val data = mutableListOf<Location>()



    override fun getLocationsFlow(): Flow<List<Location>> {
        return flow {
            emit(data)
        }
    }

    override suspend fun getLocationById(locationId: String): Location? {
        val index = data.map { it.location.locationId }.indexOf(locationId)
        return data[index]
    }

    override suspend fun insert(obj: LocationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(obj: List<LocationEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(obj: LocationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun update(obj: List<LocationEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(obj: List<LocationEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(obj: LocationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getLocations(): List<Location> {
        TODO("Not yet implemented")
    }


    override suspend fun insertLocationEntity(locationEntity: LocationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLocationLabel(locationLabel: LocationLabelCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLocationCategory(locationCategory: LocationCategoryCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLocationPhoto(locationPhoto: LocationPhotoCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLocationTag(locationTag: LocationTagCrossRef) {
        TODO("Not yet implemented")
    }
}