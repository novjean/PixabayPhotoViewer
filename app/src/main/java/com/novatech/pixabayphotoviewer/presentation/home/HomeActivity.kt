package com.novatech.pixabayphotoviewer.presentation.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.novatech.pixabayphotoviewer.databinding.ActivityHomeBinding
import com.novatech.pixabayphotoviewer.domain.model.Image
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

        setupRecyclerView()
        observeViewModel()

        // Load initial data
        lifecycleScope.launch {
            viewModel.loadImages()
        }
    }

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

    private fun observeViewModel() {
        viewModel.images.observe(this) { images ->
            adapter.submitList(images)
        }
    }

    private fun navigateToImageDetail(image: Image) {
        Toast.makeText(this, "Navigating to Image Detail", Toast.LENGTH_LONG).show()
//        val intent = Intent(this, ImageDetailActivity::class.java).apply {
//            putExtra("IMAGE", image)
//        }
//        startActivity(intent)
    }
}