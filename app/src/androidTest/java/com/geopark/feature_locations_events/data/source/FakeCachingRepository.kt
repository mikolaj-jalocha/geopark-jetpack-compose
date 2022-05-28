package com.geopark.feature_locations_events.data.source

import com.geopark.feature_locations_events.domain.repository.CachingRepository
import kotlinx.coroutines.delay
import java.lang.Exception

class FakeCachingRepository  : CachingRepository {

    private var isException = false

    fun changeExceptionStatus(shouldExceptionBeThrown : Boolean){
        isException = shouldExceptionBeThrown
    }

    override suspend fun cacheData() {
        if(isException)
                throw Exception("Exception which cancels the worker has been thrown")
        else
            delay(3000)
    }
}