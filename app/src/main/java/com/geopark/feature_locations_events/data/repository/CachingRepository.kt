package com.geopark.feature_locations_events.data.repository

import android.util.Log
import com.geopark.feature_locations_events.data.local.dao.*
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import kotlinx.coroutines.*

class CachingRepository(
    private val api: GeoparkApi,
    private val organizerDao: OrganizerDao,
    private val tagDao: TagDao,
    private val labelDao: LabelDao,
    private val photoDao: PhotoDao,
    private val categoryDao: CategoryDao,
    private val eventDao: EventDao,
    private val locationDao: LocationDao,
    val deleteAll: suspend () -> Unit
) {

    private suspend fun deleteData() {
        deleteAll()
    }

    suspend fun cacheData()  = coroutineScope(){
                withContext(Dispatchers.IO) {
                    val organizers = api.getOrganizers()
                    val tags = api.getTags()
                    val labels = api.getLabels()
                    val photos = api.getPhotos()
                    val categories = api.getCategories()
                    val events = api.getEvents()
                    val locations = api.getLocations()

                    deleteData()

                    organizerDao.insert(organizers)
                    tagDao.insert(tags)
                    labelDao.insert(labels)
                    photoDao.insert(photos)
                    categoryDao.insert(categories)
                    locationDao.addLocations(locations)
                    eventDao.addEvents(events)
                }
    }
}