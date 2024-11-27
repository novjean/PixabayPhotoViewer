package com.novatech.pixabayphotoviewer.data.repository

import com.novatech.pixabayphotoviewer.data.datasource.LocalDataSource
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository

class UserRepositoryImpl(private val localDataSource: LocalDataSource) : UserRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return localDataSource.mockLogin(email, password)
    }

    override suspend fun register(user: User): Result<Unit> {
        return localDataSource.mockRegister(user)
    }
}