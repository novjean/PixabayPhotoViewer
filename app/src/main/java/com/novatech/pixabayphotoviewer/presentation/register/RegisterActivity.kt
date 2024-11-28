package com.novatech.pixabayphotoviewer.presentation.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.novatech.pixabayphotoviewer.databinding.ActivityRegisterBinding
import com.novatech.pixabayphotoviewer.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeViewModel()

        binding.cancelButton.setOnClickListener{
            finish()
        }

    }

    private fun observeViewModel() {
        viewModel.emailError.observe(this) { error ->
            binding.emailInputLayout.error = error
        }

        viewModel.passwordError.observe(this) { error ->
            binding.passwordInputLayout.error = error
        }

        viewModel.ageError.observe(this) { error ->
            binding.ageInputLayout.error = error
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.registerButton.isEnabled = !isLoading
        }

        viewModel.registerResult.observe(this) { result ->
            result!!.onSuccess {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }.onFailure {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}