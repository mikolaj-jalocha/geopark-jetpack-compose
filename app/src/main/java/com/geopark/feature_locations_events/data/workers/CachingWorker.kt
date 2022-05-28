package com.geopark.feature_locations_events.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.geopark.feature_locations_events.data.repository.CachingRepositoryImpl
import com.geopark.feature_locations_events.domain.repository.CachingRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class CachingWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val cachingRepositoryImpl: CachingRepository
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        try {
            cachingRepositoryImpl.cacheData()
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}
