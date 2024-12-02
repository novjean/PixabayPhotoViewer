package com.novatech.pixabayphotoviewer.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.novatech.pixabayphotoviewer.databinding.ActivityLoginBinding
import com.novatech.pixabayphotoviewer.presentation.home.HomeActivity
import com.novatech.pixabayphotoviewer.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeViewModel()

        binding.btnRegister.setOnClickListener {
            // Navigate to Registration
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Observes changes in ViewModel LiveData and updates the UI accordingly.
     *
     * Handles email and password validation errors, loading states, and login success/failure events.
     */
    private fun observeViewModel() {
        viewModel.emailError.observe(this) { error ->
            binding.emailInputLayout.error = error
        }

        viewModel.passwordError.observe(this) { error ->
            binding.passwordInputLayout.error = error
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.loginButton.isEnabled = !isLoading
        }

        viewModel.loginResult.observe(this) { result ->
            result!!.onSuccess {
                // Navigate to Home Screen
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }.onFailure {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}