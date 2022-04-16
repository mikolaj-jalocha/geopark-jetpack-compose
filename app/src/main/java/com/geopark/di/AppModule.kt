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
import com.geopark.feature_locations_events.domain.use_case.events.*
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
    fun provideGetOrderedAndFilteredLocationsUseCase(getFilteredByQueryAndTypeLocationsUseCase: GetFilteredByQueryAndTypeLocationsUseCase) =
        GetOrderedAndFilteredLocationsUseCase(getFilteredByQueryAndTypeLocationsUseCase)

    @Provides
    @Singleton
    fun provideGetFilteredLocationsUseCase(
        getLocationsByType: GetLocationsByTypeUseCase,
    ) = GetFilteredByQueryAndTypeLocationsUseCase(getLocationsByType)

    @Provides
    @Singleton
    fun provideGetLocationByIdUseCase(repository: LocationRepository) =
        GetLocationByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideLocationUseCases(
        getLocationsByType: GetLocationsByTypeUseCase,
        getOrderedAndFilteredLocationsUseCase: GetOrderedAndFilteredLocationsUseCase,
        getFilteredByQueryAndTypeLocationsUseCase: GetFilteredByQueryAndTypeLocationsUseCase,
        getLocationByIdUseCase: GetLocationByIdUseCase

    ) = LocationUseCases(
        getLocationsByType,
        getOrderedAndFilteredLocationsUseCase,
        getFilteredByQueryAndTypeLocationsUseCase,
        getLocationByIdUseCase
    )


    @Provides
    @Singleton
    fun provideEventsUseCases(repository: EventRepository): EventsUseCase {
        return EventsUseCase(
            getAllEventsFlowUseCase = GetAllEventsFlowUseCase(repository = repository),
            getEventsForCategoryUseCase = GetEventsForCategoryUseCase(
                GetAllEventsFlowUseCase(
                    repository = repository
                )
            ),
            getEventsForDateAndCategoryUseCase = GetEventsForDateAndCategoryUseCase(
                GetEventsForCategoryUseCase(
                    GetAllEventsFlowUseCase(repository = repository)
                )
            )
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
            db.locationDao
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