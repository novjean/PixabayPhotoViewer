package com.novatech.pixabayphotoviewer.di

import android.content.Context
import com.novatech.pixabayphotoviewer.data.datasource.LocalDataSource
import com.novatech.pixabayphotoviewer.data.repository.UserRepositoryImpl
import com.novatech.pixabayphotoviewer.domain.repository.ImageRepository
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository
import com.novatech.pixabayphotoviewer.domain.use_case.fetch_images.FetchImagesUseCase
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCase
import com.novatech.pixabayphotoviewer.domain.use_case.register.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

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

    @Provides
    fun provideFetchImagesUseCase(repository: ImageRepository): FetchImagesUseCase {
        return FetchImagesUseCase(repository)
    }

}