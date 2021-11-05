package com.geopark.di

import android.app.Application
import androidx.room.Room
import com.geopark.feature_locations.data.data_source.LocationDao
import com.geopark.feature_locations.data.data_source.LocationDatabase
import com.geopark.feature_locations.data.repository.LocationRepositoryImpl
import com.geopark.feature_locations.domain.repository.LocationRepository
import com.geopark.feature_locations.domain.use_case.*
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(app: Application,
    callback : LocationDatabase.Callback): LocationDatabase {
        return Room.databaseBuilder(
            app,LocationDatabase::class.java,
            LocationDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @LocationsQuery
    @Provides
    @Singleton
    fun provideQueryLocations() = FirebaseFirestore.getInstance()
        .collection("Locations")
        .limit(500)



    @Provides
    @Singleton
    fun provideLocationRepository(db : LocationDatabase)  : LocationRepository {
            return LocationRepositoryImpl(db.locationDao)
    }

    @Provides
    @Singleton
    fun provideLocationUseCases(repository: LocationRepository): LocationUseCases {
        return LocationUseCases(
            getLocations = GetLocations(repository),
            getLocationByName = GetLocationByName(repository),
            insertLocations = InsertLocations(repository),
            changeLocationData = ChangeLocationData(repository)
        )
    }


    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class LocationsQuery


}