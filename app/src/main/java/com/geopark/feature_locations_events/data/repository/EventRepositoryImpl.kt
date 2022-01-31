package com.geopark.feature_locations_events.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.geopark.core.util.Constants
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.dao.EventDao
import com.geopark.feature_locations_events.data.local.dao.OrganizerDao
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventRepositoryImpl(
    private val api: GeoparkApi,
    private val dao: EventDao,
    private val organizerDao : OrganizerDao,
    private val preferences: SharedPreferences
) : EventRepository {

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {

        emit(Resource.Loading(data = emptyList()))

        val lastUpdateDate = preferences.getString(Constants.EVENTS_UPDATE_DATE, "")
        val daysFromLastUpdate = if (!lastUpdateDate.isNullOrEmpty())
            LocalDate.now().dayOfYear - LocalDate.parse(
                lastUpdateDate,
                DateTimeFormatter.ISO_DATE
            ).dayOfYear else -1

        val localEvents = dao.getEvents()
        emit(Resource.Loading(localEvents))

        try {
            val remoteEvents = api.getEvents()
            val remoteOrganizers = api.getOrganizers()

            organizerDao.insert(remoteOrganizers)


            remoteEvents.forEach {
                dao.addEvent(it)
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
            Log.d("EVENT_REPOSITORY", "${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error occurred", data = emptyList()))
        }


        /* if (lastUpdateDate != null && (lastUpdateDate.isEmpty() || daysFromLastUpdate > Constants.DAYS_FOR_EVENTS_API_UPDATE)) {

             try {
                 val remoteEvents = api.getEvents()

                 dao.insert(remoteEvents)

                 //Log.d("EVENT_REPOSITORY", "${remoteEvents[0].category.size}")
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
                 Log.d("EVENT_REPOSITORY", "${e.message}")
                 emit(Resource.Error(e.message ?: "Unknown error occurred", data = emptyList()))
             }
         }*/
        emit(Resource.Success(dao.getEvents()))
    }


}