package com.geopark.feature_locations_events.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.geopark.core.util.Constants
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.dao.EventDao
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventRepositoryImpl(
    private val dao: EventDao,
    private val preferences: SharedPreferences,
    private val cachingRepository: CachingRepository
) : EventRepository {

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {

        emit(Resource.Loading(data = emptyList()))
        val localEvents = dao.getEvents()
        emit(Resource.Loading(localEvents))
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
                         data = emptyList()
                     )
                 )
             } catch (e: NoInternetConnection) {
                 emit(Resource.Error(e.message, data = emptyList()))
             } catch (e: Exception) {
                 Log.d("EVENT_REPOSITORY", "${e.message} ")
                 emit(Resource.Error(e.message ?: "Unknown error occurred", data = emptyList()))
             }
         }
        emit(Resource.Success(dao.getEvents()))
    }


}