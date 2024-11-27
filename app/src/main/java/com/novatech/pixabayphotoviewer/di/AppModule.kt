package com.novatech.pixabayphotoviewer.di

import android.content.Context
import androidx.room.Room
import com.novatech.pixabayphotoviewer.common.Constants.BASE_URL
import com.novatech.pixabayphotoviewer.common.Constants.DATABASE_NAME
import com.novatech.pixabayphotoviewer.data.datasource.LocalDataSource
import com.novatech.pixabayphotoviewer.data.local.UserDatabase
import com.novatech.pixabayphotoviewer.data.remote.PixabayAPI
import com.novatech.pixabayphotoviewer.data.repository.UserRepositoryImpl
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCase
import com.novatech.pixabayphotoviewer.domain.use_case.register.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSource()
    }

    @Provides
    fun provideUserRepository(localDataSource: LocalDataSource): UserRepository {
        return UserRepositoryImpl(localDataSource)
    }

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Provides
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providesUserDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, UserDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: UserDatabase
    ) = database.userDao()

    @Singleton
    @Provides
    fun providesPixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(PixabayAPI::class.java)
    }
}