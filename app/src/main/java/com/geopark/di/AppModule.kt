package com.geopark.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.geopark.core.util.Constants
import com.geopark.core.util.Constants.DATABASE_NAME
import com.geopark.feature_locations_events.data.local.Converters
import com.geopark.feature_locations_events.data.local.GeoparkDatabase
import com.geopark.feature_locations_events.data.remote.ConnectivityInterceptor
import com.geopark.feature_locations_events.data.remote.GeoparkApi
import com.geopark.feature_locations_events.data.repository.CachingRepository
import com.geopark.feature_locations_events.data.repository.EventRepositoryImpl
import com.geopark.feature_locations_events.data.repository.LocationRepositoryImpl
import com.geopark.feature_locations_events.data.util.GsonParser
import com.geopark.feature_locations_events.domain.repository.EventRepository
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import com.geopark.feature_locations_events.domain.use_case.events.EventsUseCase
import com.geopark.feature_locations_events.domain.use_case.events.GetAllEvents
import com.geopark.feature_locations_events.domain.use_case.events.GetAllEventsDistinct
import com.geopark.feature_locations_events.domain.use_case.events.GetAllEventsFlowUseCase
import com.geopark.feature_locations_events.domain.use_case.locations.*
import com.google.gson.Gson
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
        // TODO: Rename variable name
        return app.getSharedPreferences("GEOPARK_SETTINGS", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideGeoparkDatabase(
        app: Application,
    ): GeoparkDatabase {
        return Room.databaseBuilder(
            app, GeoparkDatabase::class.java,
            DATABASE_NAME
        ).addTypeConverter(Converters(GsonParser(Gson())))
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
            .baseUrl(Constants.GEOPARK_API_URL)
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
        db: GeoparkDatabase
    ): LocationRepository {
        return LocationRepositoryImpl(db.locationDao)
    }


    @Provides
    @Singleton
    fun provideGetAllLocationsUseCase(repository: LocationRepository) =
        GetAllLocationsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLocationsByTypeUseCase(getAllLocations: GetAllLocationsUseCase) =
        GetLocationsByTypeUseCase(getAllLocations)

    @Provides
    @Singleton
    fun provideGetOrderedLocationsUseCase(getLocationsByType: GetLocationsByTypeUseCase) =
        GetOrderedLocationsUseCase(getLocationsByType)

    @Provides
    @Singleton
    fun provideGetFilteredLocationsUseCase(
        getLocationsByType: GetLocationsByTypeUseCase,
        getOrderedLocationsUseCase: GetOrderedLocationsUseCase
    ) = GetFilteredLocationsUseCase(getLocationsByType, getOrderedLocationsUseCase)


    @Provides
    @Singleton
    fun provideLocationUseCases(
        allLocationsUseCase: GetAllLocationsUseCase,
        getLocationsByType: GetLocationsByTypeUseCase,
        getOrderedLocationsUseCase: GetOrderedLocationsUseCase,
        getFilteredLocationsUseCase: GetFilteredLocationsUseCase

    ) = LocationUseCases(
        allLocationsUseCase,
        getLocationsByType,
        getOrderedLocationsUseCase,
        getFilteredLocationsUseCase
    )


/*    @Provides
    @Singleton
    fun provideLocationUseCases(
        getAllLocations: GetAllLocationsFlowUseCase,
        getLocationsByType: GetLocationsByTypeUseCase,
        getOrderedLocationsUseCase: GetOrderedLocationsFlowUseCase,
        getFilteredLocationsUseCase: GetFilteredLocationsFlowUseCase
    ): LocationUseCases {
        return LocationUseCases(
            getAllLocationsFlowUseCase =getAllLocations,
            getLocationsByTypeUseCase = getLocationsByType,
            getOrderedLocationsFlowUseCase = GetOrderedLocationsFlowUseCase(
                getLocationsByType = GetLocationsByTypeUseCase(GetAllLocationsFlowUseCase(repository))
            ),
            getFilteredLocationsFlowUseCase = GetFilteredLocationsFlowUseCase(
                getLocationsByType = GetLocationsByTypeUseCase(
                    GetAllLocationsFlowUseCase(
                        repository
                    )
                ), GetOrderedLocationsFlowUseCase(
                    getLocationsByType = GetLocationsByTypeUseCase(
                        GetAllLocationsFlowUseCase(
                            repository
                        )
                    )
                )
            )
        )
    }*/

    @Provides
    @Singleton
    fun provideEventsUseCases(repository: EventRepository): EventsUseCase {
        return EventsUseCase(
            getAllEvents = GetAllEvents(repository = repository),
            getAllEventsFlowUseCase = GetAllEventsFlowUseCase(repository = repository),
            getAllEventsDistinct = GetAllEventsDistinct(repository = repository),
        )
    }


    @Provides
    @Singleton
    fun provideEventRepository(
        db: GeoparkDatabase,
    ): EventRepository {
        return EventRepositoryImpl(db.eventDao)
    }

    @Provides
    @Singleton
    fun provideCachingRepository(
        api: GeoparkApi,
        db: GeoparkDatabase
    ): CachingRepository {
        return CachingRepository(
            api,
            db.organizerDao,
            db.tagDao,
            db.labelDao,
            db.photoDao,
            db.categoryDao,
            db.eventDao,
            db.locationDao,
            provideApplicationScope()
        ) {
            db.clearAllTables()
        }
    }


    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ConnectivityClient


}