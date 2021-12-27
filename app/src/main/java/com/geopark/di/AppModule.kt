package com.geopark.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.geopark.core.util.Constans
import com.geopark.feature_locations_events.data.local.EventDao
import com.geopark.feature_locations_events.data.local.LocationDatabase
import com.geopark.feature_locations_events.data.remote.ConnectivityInterceptor
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import com.geopark.feature_locations_events.data.repository.EventRepositoryImpl
import com.geopark.feature_locations_events.data.repository.LocationRepositoryImpl
import com.geopark.feature_locations_events.domain.repository.EventRepository
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import com.geopark.feature_locations_events.domain.use_case.events.EventsUseCase
import com.geopark.feature_locations_events.domain.use_case.events.GetEvents
import com.geopark.feature_locations_events.domain.use_case.locations.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        // TODO: 27.12.2021 Rename variable name
        return app.getSharedPreferences("GEOPARK_CACHE_SETTINGS", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideLocationDatabase(
        app: Application,
    ): LocationDatabase {
        return Room.databaseBuilder(
            app, LocationDatabase::class.java,
            Constans.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    @ConnectivityClient
    fun provideOkHttpClientConnectivityInterceptor(app: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor(context = app.applicationContext))
            .build()
    }

    @Provides
    @Singleton
    fun provideGeoparkApi(@ConnectivityClient connectivityClient: OkHttpClient): GeoparkApi {
        return Retrofit.Builder()
            .baseUrl(Constans.GEOPARK_API_URL)
            .client(connectivityClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoparkApi::class.java)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideLocationRepository(
        db: LocationDatabase,
        api: GeoparkApi,
        preferences: SharedPreferences
    ): LocationRepository {
        return LocationRepositoryImpl(db.locationDao, api, preferences)
    }

    @Provides
    @Singleton
    fun provideLocationUseCases(repository: LocationRepository): LocationUseCases {
        return LocationUseCases(
            getLocations = GetLocations(repository),
            getLocationByName = GetLocationByName(repository)
        )
    }

    @Provides
    @Singleton
    fun provideEventRepository(api: GeoparkApi,db: LocationDatabase)  :  EventRepository {
        return EventRepositoryImpl(api,db.eventDao)
    }

    @Provides
    @Singleton
    fun provideEventsUseCases(repository: EventRepository): EventsUseCase {
        return EventsUseCase(getEvents = GetEvents(repository))
    }


    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ConnectivityClient

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class LocationsQuery


}