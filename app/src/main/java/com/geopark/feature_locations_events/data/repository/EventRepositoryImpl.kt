package com.geopark.feature_locations_events.data.repository

import android.util.Log
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception

class EventRepositoryImpl(
    private val api : GeoparkApi
) : EventRepository {

    override fun getEvents()  : Flow<Resource<List<Event>>> = flow {

        emit(Resource.Loading(data = emptyList()))
        try {
            val remoteEvents = api.getEvents()
            Log.d("REPO_EVENTS", "remoteEvents size = ${remoteEvents.size}")
            emit(Resource.Success(data = remoteEvents))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred", data = emptyList()))
        } catch (e: NoInternetConnection) {
            emit(Resource.Error(e.message, data = emptyList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred", data = emptyList()))
        }
    }
}