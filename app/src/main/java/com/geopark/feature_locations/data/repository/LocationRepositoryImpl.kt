package com.geopark.feature_locations.data.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations.data.local.LocationDao
import com.geopark.feature_locations.data.remote.GeoparkApi
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LocationRepositoryImpl(
    private val dao: LocationDao,
    private val api: GeoparkApi
) : LocationRepository {


    override suspend fun updateLocation(location: Location) {


        dao.updateLocation(location)
    }

    override fun getLocations(): Flow<Resource<List<Location>>> = flow {



        emit(Resource.Loading())
        //val locations = dao.getLocations().toList().flatten()
        try {
            val remoteLocations = api.getLocations()
            emit(Resource.Success(remoteLocations))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
        }
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