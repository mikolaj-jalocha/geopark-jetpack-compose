package com.geopark.feature_locations_events.presentation

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.geopark.core.util.Constants.IS_DATABASE_INITIALIZED
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.data.repository.CachingRepository
import com.geopark.feature_locations_events.data.workers.CachingWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    private val geoparkSettings: SharedPreferences,
    private val cachingRepository: CachingRepository
) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(this.getApplication())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun applyCaching() {
        if (!geoparkSettings.getBoolean(IS_DATABASE_INITIALIZED, false)) {
            viewModelScope.launch {
                try {
                    cachingRepository.cacheData()
                    geoparkSettings.edit().putBoolean(IS_DATABASE_INITIALIZED, true).apply()
                } catch (e: Exception) {
                    _eventFlow.emit(UiEvent.ShowSnackbar("${e.message}"))

                } finally {

                    val cachingRequest = PeriodicWorkRequestBuilder<CachingWorker>(
                        MIN_PERIODIC_INTERVAL_MILLIS,
                        TimeUnit.MILLISECONDS
                    )
                        .setConstraints(Constraints.Builder().setRequiresDeviceIdle(true).setRequiredNetworkType(NetworkType.CONNECTED).build())
                        .build()

                    workManager.enqueueUniquePeriodicWork(
                        "CACHING_PERIODIC_WORK",
                        ExistingPeriodicWorkPolicy.KEEP,
                        cachingRequest
                    )
                }
            }
        }
        }
    }
