package com.geopark.feature_locations_events.data.workers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import androidx.work.testing.TestListenableWorkerBuilder
import com.geopark.feature_locations_events.data.source.FakeCachingRepository
import com.geopark.feature_locations_events.domain.repository.CachingRepository
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CachingWorkerImplementationTest {

    private lateinit var context: Context
    private lateinit var repo: FakeCachingRepository

    class CachingWorkerFactory(private val repo: CachingRepository) : WorkerFactory() {
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            return CachingWorker(appContext, workerParameters, repo)
        }
    }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        repo = FakeCachingRepository()
    }

    @Test
    fun returnedResultIsFailureWhenExceptionIsThrown() {
        runBlocking {
            repo.changeExceptionStatus(true)

            val worker = TestListenableWorkerBuilder<CachingWorker>(context)
                .setWorkerFactory(CachingWorkerFactory(repo))
                .build()
            val result = worker.doWork()
            assertThat(result is ListenableWorker.Result.Failure).isTrue()
        }
    }

    @Test
    fun returnedResultIsSuccessWhenThereIsNoExceptionThrown() {
        runBlocking {
            repo.changeExceptionStatus(false)
            val worker = TestListenableWorkerBuilder<CachingWorker>(context)
                .setWorkerFactory(CachingWorkerFactory(repo))
                .build()
            val result = worker.doWork()
            assertThat(result is ListenableWorker.Result.Success).isTrue()
        }
    }


}