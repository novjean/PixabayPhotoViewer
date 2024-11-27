package com.novatech.pixabayphotoviewer.domain.repository

import com.novatech.pixabayphotoviewer.domain.model.User

interface UserRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(user: User): Result<Unit>
}
