package com.geopark.feature_locations_events.data.repository

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import com.geopark.core.util.Constants
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.dao.LocationDao
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocationRepositoryImpl(
    private val dao: LocationDao,
    private val preferences: SharedPreferences,
    private val cachingRepository: CachingRepository
) : LocationRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLocations(): Flow<Resource<List<Location>>> = flow {

        emit(Resource.Loading(data = emptyList()))
        val localLocations = dao.getLocations()
        emit(Resource.Loading(localLocations))


        val lastUpdateDate = preferences.getString(Constants.CACHING_UPDATE_DATE, "")

        val daysFromLastUpdate = if (!lastUpdateDate.isNullOrEmpty())
            LocalDate.now().dayOfYear - LocalDate.parse(
                lastUpdateDate,
                DateTimeFormatter.ISO_DATE
            ).dayOfYear else -1


        if (lastUpdateDate != null && (lastUpdateDate.isEmpty() || daysFromLastUpdate > Constants.DAYS_FOR_CACHING_UPDATE)) {
            try {
                cachingRepository.cacheData()
                preferences.edit {
                    putString(Constants.CACHING_UPDATE_DATE, LocalDate.now().toString())
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occurred",
                        data = localLocations
                    )
                )
            } catch (e: NoInternetConnection) {
                emit(Resource.Error(e.message, data = localLocations))
            } catch (e: Exception) {
                //emit(Resource.Error("Unknown error occurred", data = localLocations))
                emit(Resource.Error(e.message ?: "Unknown Error", data = localLocations))
            }

        }

        emit(Resource.Success(dao.getLocations()))

    }

    override suspend fun getLocationById(id: String): Location? {
        return dao.getLocationById(id)
    }
}