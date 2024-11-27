package com.novatech.pixabayphotoviewer.presentation.login

import android.content.Intent
import android.os.Bundle
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

        viewModel.loginResult.observe(this) { result ->
            result!!.onSuccess {
                // Navigate to HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

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