package com.geopark.feature_locations_events.data.source

import com.geopark.feature_locations_events.domain.repository.CachingRepository
import java.lang.Exception

class FakeCachingRepository  : CachingRepository {


    override suspend fun cacheData() {

        throw Exception()
    }
}