package com.novatech.pixabayphotoviewer.domain.use_case.register

import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository

/**
 * Use case for handling user registration.
 * Registers the user and validates their input data.
 *
 * @param user The [User] object containing registration details.
 * @return A [Result] indicating success or failure.
 */
class RegisterUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return userRepository.register(user)
    }
}