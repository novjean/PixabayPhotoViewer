package com.novatech.pixabayphotoviewer.domain.use_case.login

import com.novatech.pixabayphotoviewer.domain.model.User

interface LoginUseCaseWrapper {
    suspend fun execute(email: String, password: String): Result<User>
}
