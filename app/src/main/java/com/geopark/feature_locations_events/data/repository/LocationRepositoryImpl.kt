package com.geopark.feature_locations_events.data.repository

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.dao.LocationDao
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl(
    private val dao: LocationDao,
) : LocationRepository {


  override fun getLocationsFlow() = dao.getLocationsFlow().map {
        if (it.isEmpty()) {
            Resource.Loading(data = emptyList())
        }
        else
            Resource.Success(data = it)
    }

    override suspend fun getLocationById(id: String): Location? {
        return dao.getLocationById(id)
    }
}