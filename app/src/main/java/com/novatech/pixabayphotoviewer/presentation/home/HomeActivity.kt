package com.novatech.pixabayphotoviewer.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.novatech.pixabayphotoviewer.R
import com.novatech.pixabayphotoviewer.databinding.ActivityHomeBinding
import com.novatech.pixabayphotoviewer.domain.model.Image
import com.novatech.pixabayphotoviewer.presentation.image_detail.ImageDetailActivity
import com.novatech.pixabayphotoviewer.presentation.login.LoginActivity
import com.novatech.pixabayphotoviewer.util.SecurePreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ImageAdapter { image ->
        navigateToImageDetail(image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        setupRecyclerView()
        observeViewModel()

        // Load initial data
        lifecycleScope.launch {
            viewModel.loadImages()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)

        // Display the user email in the menu
        val emailItem = menu?.findItem(R.id.email_display)
        val userEmail = SecurePreferences.getEmail(this)
        emailItem?.title = userEmail ?: "No email available"

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // Clear saved credentials
                SecurePreferences.clearCredentials(this)
                navigateToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Initializes the RecyclerView for displaying image data.
     *
     * Sets up the layout manager, adapter, and scroll listener for pagination.
     */
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = this@HomeActivity.adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        // Load next page
                        viewModel.loadImages()
                    }
                }
            })
        }
    }

    /**
     * Observes ViewModel LiveData for image data and updates the UI accordingly.
     *
     * Ensures the UI reacts to changes in the image list fetched from the ViewModel.
     */
    private fun observeViewModel() {
        viewModel.images.observe(this) { images ->
            adapter.submitList(images)
        }
    }

    /**
     * Navigates to the Image Detail screen for a selected image.
     *
     * @param image The [Image] object containing the details to display.
     *
     * Passes the image data to the Image Detail Activity using an Intent.
     */
    private fun navigateToImageDetail(image: Image) {
        val intent = Intent(this, ImageDetailActivity::class.java).apply {
            // Pass the Image object to the detail screen
            putExtra("IMAGE", image)
        }
        startActivity(intent)
    }

    /**
     * Logs out the current user and navigates back to the Login screen.
     *
     * Clears saved credentials and resets the navigation stack to prevent back navigation.
     */
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}