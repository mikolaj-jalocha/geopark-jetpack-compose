package com.geopark.feature_locations_events.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.geopark.core.util.Constants
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.EventDao
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventRepositoryImpl(
    private val api : GeoparkApi,
    private val dao: EventDao,
    private val preferences: SharedPreferences
) : EventRepository {

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {

        emit(Resource.Loading(data = emptyList()))

        val lastUpdateDate = preferences.getString(Constants.EVENTS_UPDATE_DATE, "")
        val daysFromLastUpdate =   if(!lastUpdateDate.isNullOrEmpty())
            LocalDate.now().dayOfYear - LocalDate.parse(lastUpdateDate, DateTimeFormatter.ISO_DATE).dayOfYear else -1


        val localEvents = dao.getEvents()
        emit(Resource.Loading(localEvents))

        if (lastUpdateDate != null && (lastUpdateDate.isEmpty() || daysFromLastUpdate > Constants.DAYS_FOR_EVENTS_API_UPDATE)) {

            try {
                val remoteEvents = api.getEvents()
                dao.insertEvents(remoteEvents)

                // update  caching preferences only when connection was successful
                if (remoteEvents.isNotEmpty()) {
                    preferences.edit {
                        putString(Constants.EVENTS_UPDATE_DATE, LocalDate.now().toString())
                    }
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
                emit(Resource.Error(e.message ?: "Unknown error occurred", data = emptyList()))
            }
        }
        emit(Resource.Success(dao.getEvents()))
    }
}