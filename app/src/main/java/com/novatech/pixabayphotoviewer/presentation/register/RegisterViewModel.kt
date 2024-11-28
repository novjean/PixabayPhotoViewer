package com.novatech.pixabayphotoviewer.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.use_case.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val age = MutableLiveData<String>()

    val isLoading = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val ageError = MutableLiveData<String?>()

    val registerResult = MutableLiveData<Result<Unit>?>()

    init {
        // Clear email error dynamically
        email.observeForever { emailValue ->
            if (isValidEmail(emailValue)) emailError.value = null
        }

        // Clear password error dynamically
        password.observeForever { passwordValue ->
            if (isValidPassword(passwordValue)) passwordError.value = null
        }

        // Clear age error dynamically
        age.observeForever { ageValue ->
            if (isValidAge(ageValue)) ageError.value = null
        }
    }

    fun register() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()
        val ageValue = age.value?.toIntOrNull()

        var isErrorFound = false

        // Validate email
        if (!isValidEmail(emailValue)) {
            emailError.value = "Invalid email address"
            isErrorFound = true
        }

        // Validate password
        if (!isValidPassword(passwordValue)) {
            passwordError.value = "Password must be 6-12 characters"
            isErrorFound = true
        }

        // Validate age
        if (!isValidAge(ageValue.toString())) {
            ageError.value = "Age must be between 18 and 99"
            isErrorFound = true
        }

        if(isErrorFound) return

        isLoading.value = true
        emailError.value = null
        passwordError.value = null
        ageError.value = null

        if (isValidAge(ageValue.toString())) {
            viewModelScope.launch {
                registerResult.value = registerUseCase(
                    User(
                        email = email.value.orEmpty(),
                        password = password.value.orEmpty(),
                        age = ageValue
                    )
                )
                isLoading.value = false
            }
        } else {
            registerResult.value = Result.failure(Exception("Invalid age input"))
            isLoading.value = false
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length in 6..12
    }

    private fun isValidAge(age: String): Boolean {
        val ageValue = age.toIntOrNull()
        return ageValue != null && ageValue in 18..99
    }
}