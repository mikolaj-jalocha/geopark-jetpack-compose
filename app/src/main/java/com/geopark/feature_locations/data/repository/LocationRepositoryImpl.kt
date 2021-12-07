package com.geopark.feature_locations.data.repository

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import com.geopark.core.util.Constans
import com.geopark.core.util.Resource
import com.geopark.feature_locations.data.local.LocationDao
import com.geopark.feature_locations.data.remote.GeoparkApi
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocationRepositoryImpl(
    private val dao: LocationDao,
    private val api: GeoparkApi,
    private val preferences: SharedPreferences
) : LocationRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLocations(): Flow<Resource<List<Location>>> = flow {

        emit(Resource.Loading())

        val a = preferences.getString(
            "API_UPDATE_DATE",
            LocalDate.now().minusDays((Constans.DAYS_FOR_API_UPDATE + 1).toLong()).toString()
        )

        val localLocations = dao.getLocations()
        emit(Resource.Loading(localLocations))


        if (!a.isNullOrEmpty() &&
            LocalDate.now().dayOfYear - LocalDate.parse(
                a,
                DateTimeFormatter.ISO_DATE
            ).dayOfYear > Constans.DAYS_FOR_API_UPDATE.toLong()
        ) {
            preferences.edit {
                putString("API_UPDATE_DATE", LocalDate.now().toString())
            }


            try {
                val remoteLocations = api.getLocations()
                dao.insertLocations(remoteLocations)
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occured",
                        data = localLocations
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server", data = localLocations))
            }
        }
        emit(Resource.Success(dao.getLocations()))
    }


    override suspend fun updateLocation(location: Location) {
        dao.updateLocation(location)
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