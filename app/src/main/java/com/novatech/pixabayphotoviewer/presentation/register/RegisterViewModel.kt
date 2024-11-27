package com.novatech.pixabayphotoviewer.presentation.register

import android.widget.Toast
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
    val registerResult = MutableLiveData<Result<Unit>?>()

    fun register() {
        val ageValue = age.value?.toIntOrNull()
        if (ageValue != null) {
            viewModelScope.launch {
                registerResult.value = registerUseCase(
                    User(
                        email = email.value.orEmpty(),
                        password = password.value.orEmpty(),
                        age = ageValue
                    )
                )
            }
        } else {
            registerResult.value = Result.failure(Exception("Invalid age input"))
        }
    }
}