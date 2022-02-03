package com.geopark.feature_locations_events.data.repository

import com.geopark.di.AppModule
import com.geopark.feature_locations_events.data.local.dao.*
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CachingRepository(
    private val api: GeoparkApi,
    private val organizerDao: OrganizerDao,
    private val tagDao: TagDao,
    private val labelDao: LabelDao,
    private val photoDao: PhotoDao,
    private val categoryDao: CategoryDao,
    private val eventDao: EventDao,
    private val locationDao: LocationDao,
    private val scope : CoroutineScope,
    val deleteAll : suspend () -> Unit
)  {


    private fun deleteData() = scope.launch(Dispatchers.IO){
            deleteAll()
    }

    @Throws
    suspend fun cacheData() {
        deleteData()
        organizerDao.insert(api.getOrganizers())
        tagDao.insert(api.getTags())
        labelDao.insert(api.getLabels())
        photoDao.insert(api.getPhotos())
        categoryDao.insert(api.getCategories())
        eventDao.addEvents(api.getEvents())
        locationDao.addLocations(api.getLocations())
    }
}