package com.geopark.di

import android.app.Application
import androidx.room.Room
import com.geopark.feature_locations.data.data_source.LocationDao
import com.geopark.feature_locations.data.data_source.LocationDatabase
import com.geopark.feature_locations.data.repository.LocationRepositoryImpl
import com.geopark.feature_locations.domain.repository.LocationRepository
import com.geopark.feature_locations.domain.use_case.ChangeLocationData
import com.geopark.feature_locations.domain.use_case.GetLocations
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(app: Application): LocationDatabase {
        return Room.databaseBuilder(
            app,LocationDatabase::class.java,
            LocationDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFirebaseLocationsReference() = FirebaseFirestore.getInstance().collection("Locations")

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
            changeLocationData = ChangeLocationData(repository)
        )
    }


}