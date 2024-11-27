package com.novatech.pixabayphotoviewer.data.datasource

import com.novatech.pixabayphotoviewer.domain.model.User

class LocalDataSource {
    fun mockLogin(email: String, password: String): Result<User> {
        if (email == "test@test.com" && password == "password123") {
            return Result.success(User(email, password))
        }
        return Result.failure(Exception("Invalid login credentials"))
    }

    fun mockRegister(user: User): Result<Unit> {
        if (user.age in 18..99) {
            return Result.success(Unit)
        }
        return Result.failure(Exception("Age must be between 18 and 99"))
    }
}