package com.novatech.pixabayphotoviewer.di

import com.novatech.pixabayphotoviewer.common.Constants
import com.novatech.pixabayphotoviewer.data.datasource.RemoteDataSource
import com.novatech.pixabayphotoviewer.data.remote.PixabayAPI
import com.novatech.pixabayphotoviewer.data.repository.ImageRepositoryImpl
import com.novatech.pixabayphotoviewer.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesPixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

    @Provides
    fun provideRemoteDataSource(api: PixabayAPI): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    fun provideImageRepository(remoteDataSource: RemoteDataSource): ImageRepository {
        return ImageRepositoryImpl(remoteDataSource)
    }

}