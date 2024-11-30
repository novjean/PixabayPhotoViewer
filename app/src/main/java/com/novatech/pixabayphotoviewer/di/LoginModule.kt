package com.novatech.pixabayphotoviewer.di

import com.novatech.pixabayphotoviewer.data.repository.LoginUseCaseWrapperImpl
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCase
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCaseWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    fun provideLoginUseCaseWrapper(loginUseCase: LoginUseCase): LoginUseCaseWrapper {
        return LoginUseCaseWrapperImpl(loginUseCase)
    }
}