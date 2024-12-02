package com.novatech.pixabayphotoviewer.presentation.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCase
import com.novatech.pixabayphotoviewer.util.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles user login functionality, including validation and interaction with the use case layer.
 * Manages UI state for the login screen.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    val loginResult = MutableLiveData<Result<User>?>()

    init {
        // Observe email input and clear error if valid
        email.observeForever { emailValue ->
            if (isValidEmail(emailValue)) {
                emailError.value = null
            }
        }

        // Observe password input and clear error if valid
        password.observeForever { passwordValue ->
            if (isValidPassword(passwordValue)) {
                passwordError.value = null
            }
        }
    }

    /**
     * Initiates the login process with the provided email and password.
     * Validates inputs and updates UI state accordingly.
     */
    fun login() {
        // Implementation...
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()
        var isErrorFound = false

        if (!isValidEmail(emailValue)) {
            emailError.value = "Invalid email address"
            isErrorFound = true
        }

        if (!isValidPassword(passwordValue)) {
            passwordError.value = "Password must be 6-12 characters"
            isErrorFound = true
        }

        if(isErrorFound) return

        isLoading.value = true
        emailError.value = null
        passwordError.value = null

        viewModelScope.launch {
            val value = loginUseCase(email.value.orEmpty(), password.value.orEmpty())

            if(value.isSuccess){
                loginResult.value = value
                SecurePreferences.saveEmail(context, emailValue) // Save email securely
            } else {
                loginResult.value = Result.failure(Exception("Invalid login credentials"))
            }
            isLoading.value = false
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length in 6..12
    }

}