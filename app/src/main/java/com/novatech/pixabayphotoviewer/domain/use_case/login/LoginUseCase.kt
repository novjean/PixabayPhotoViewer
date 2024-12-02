package com.novatech.pixabayphotoviewer.domain.use_case.login

import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository

/**
 * Use case for handling user login.
 * Validates the credentials and fetches user data from the repository.
 *
 * @param email The user's email address.
 * @param password The user's password.
 * @return A [Result] containing the [User] data or an error.
 */
class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return userRepository.login(email, password)
    }
}
