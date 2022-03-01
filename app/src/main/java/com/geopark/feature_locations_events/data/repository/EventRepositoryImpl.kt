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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventRepositoryImpl(
    private val dao: EventDao,
) : EventRepository {

    override fun getEventsFlow() = dao.getEventsFlow().map {
        if (it.isEmpty()) {
            Resource.Loading(data = emptyList())
        }
        else
            Resource.Success(data = it)
    }


    override fun getEvents(): Flow<Resource<List<Event>>> = flow {

        emit(Resource.Loading(data = emptyList()))
        val localEvents = dao.getEvents()
        if (localEvents.isEmpty())
            emit(Resource.Loading(localEvents))
        else
            emit(Resource.Success(localEvents))

    }


}