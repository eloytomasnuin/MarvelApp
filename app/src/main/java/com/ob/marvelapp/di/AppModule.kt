package com.ob.marvelapp.di

import android.app.Application
import androidx.room.Room
import com.ob.data.MarvelRepository
import com.ob.data.MarvelRepositoryImplementation
import com.ob.data.datasources.LocalDataSource
import com.ob.data.datasources.RemoteDataSource
import com.ob.domain.Constants
import com.ob.marvelapp.data.local.DBMapper
import com.ob.marvelapp.data.local.db.DataBase
import com.ob.marvelapp.data.local.db.RoomDB
import com.ob.marvelapp.data.remote.JsonMapper
import com.ob.marvelapp.data.remote.RemoteMapper
import com.ob.marvelapp.data.remote.manager.ConnectionManager
import com.ob.marvelapp.data.remote.manager.ConnectivityManager
import com.ob.marvelapp.data.remote.service.ApiService
import com.ob.marvelapp.data.remote.service.RetrofitService
import com.ob.marvelapp.ui.UIMapper
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDispatcher() : CoroutineDispatcher = Dispatchers.Main

    @Singleton
    @Named("base_url")
    @Provides
    fun provideBaseUrl() = Constants.Server.BASE_URL

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(Constants.Server.OkHTTPConfig.timeOut, TimeUnit.MILLISECONDS)
        .connectTimeout(Constants.Server.OkHTTPConfig.timeOut, TimeUnit.MILLISECONDS)
        .build()

    @Singleton
    @Provides
    fun provideConnectionManager(app: Application): ConnectionManager {
        return ConnectivityManager(app)
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        @Named("base_url") baseUrl: String
    ): ApiService = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build().create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMarvelRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MarvelRepository {
        return MarvelRepositoryImplementation(remoteDataSource, localDataSource)
    }

    @Singleton
    @Provides
    fun provideDatabase(
        application: Application
    ) = Room.databaseBuilder(application, DataBase::class.java, "db")
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideRoomDB(dbMapper: DBMapper, database: DataBase): LocalDataSource {
        return RoomDB(dbMapper, database)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        apiService: ApiService,
        serverMapper: RemoteMapper,
        jsonMapper: JsonMapper,
        connectionManager: ConnectionManager
    ): RemoteDataSource = RetrofitService(
        apiService,
        serverMapper,
        jsonMapper,
        connectionManager
    )

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideDbMapper() = DBMapper()

    @Singleton
    @Provides
    fun provideServerMapper() = RemoteMapper()

    @Singleton
    @Provides
    fun provideJsonMapper(moshi: Moshi) = JsonMapper(moshi)

    @Singleton
    @Provides
    fun provideUIMapper() = UIMapper()
}