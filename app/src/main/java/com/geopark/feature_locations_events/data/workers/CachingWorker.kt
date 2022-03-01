package com.geopark.feature_locations_events.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.geopark.feature_locations_events.data.remote.NoInternetConnection
import com.geopark.feature_locations_events.data.repository.CachingRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException


@HiltWorker
class CachingWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val cachingRepository: CachingRepository
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {

        cachingRepository.cacheData()
         return   Result.success()
    }
}