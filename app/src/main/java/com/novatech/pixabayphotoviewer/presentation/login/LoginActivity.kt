package com.novatech.pixabayphotoviewer.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.novatech.pixabayphotoviewer.R
import com.novatech.pixabayphotoviewer.databinding.ActivityLoginBinding
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

        viewModel.loginResult.observe(this) { result ->
            result!!.onSuccess {
                // Navigate to Home
                Toast.makeText(this, "user logged in success", Toast.LENGTH_LONG).show()
            }.onFailure {
                // Show error
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()

            }
        }

        binding.btnRegister.setOnClickListener {
            // Navigate to Registration
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}