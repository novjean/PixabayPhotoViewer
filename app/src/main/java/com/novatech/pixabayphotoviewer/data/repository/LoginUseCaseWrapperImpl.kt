package com.novatech.pixabayphotoviewer.data.repository

import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCase
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCaseWrapper

class LoginUseCaseWrapperImpl(private val loginUseCase: LoginUseCase) : LoginUseCaseWrapper {
    override suspend fun execute(email: String, password: String): Result<User> {
        return loginUseCase(email, password)
    }
}
