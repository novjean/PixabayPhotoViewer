package com.novatech.pixabayphotoviewer.domain.use_case.login

import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Add domain-level validation or transformation if needed
        return userRepository.login(email, password)
    }
}
