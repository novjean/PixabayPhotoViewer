package com.novatech.pixabayphotoviewer.presentation.image_detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.novatech.pixabayphotoviewer.databinding.ActivityImageDetailBinding
import com.novatech.pixabayphotoviewer.domain.model.Image

class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding
    private val viewModel: ImageDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind ViewModel to the layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Retrieve the Image object passed from HomeActivity
        val image = intent.getParcelableExtra<Image>("IMAGE") ?: return
        setupUI(image)

    }

    private fun setupUI(image: Image) {
        binding.progressBar.visibility = View.VISIBLE

        // Load the image into the ImageView
        Glide.with(this)
            .load(image.fullSizeURL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE // Hide on failure
                    Toast.makeText(this@ImageDetailActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE // Hide when image loads
                    return false
                }
            })
            .into(binding.imageView)

        viewModel.setImageDetail(image)
    }

}