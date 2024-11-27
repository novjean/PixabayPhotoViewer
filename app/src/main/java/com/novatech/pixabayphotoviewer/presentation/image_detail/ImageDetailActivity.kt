package com.novatech.pixabayphotoviewer.presentation.image_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.novatech.pixabayphotoviewer.R
import com.novatech.pixabayphotoviewer.databinding.ActivityImageDetailBinding
import com.novatech.pixabayphotoviewer.domain.model.Image

class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the Image object passed from HomeActivity
        val image = intent.getParcelableExtra<Image>("IMAGE") ?: return

        // Populate the UI with the image details
        populateImageDetails(image)
    }

    private fun populateImageDetails(image: Image) {
        // Section 1: Image and Metadata
        Glide.with(this)
            .load(image.fullSizeURL)
            .into(binding.imageView)
        binding.imageSize.text = getString(R.string.image_size, image.imageSize)
        binding.imageType.text = getString(R.string.image_type, image.imageType)
        binding.imageTags.text = getString(R.string.image_tags, image.tags.joinToString(", "))

        // Section 2: User and Stats
        binding.imageUploader.text = getString(R.string.uploader_name, image.userName)
        binding.imageViews.text = getString(R.string.views, image.views)
        binding.imageLikes.text = getString(R.string.likes, image.likes)
        binding.imageComments.text = getString(R.string.comments, image.comments)
        binding.imageFavorites.text = getString(R.string.favorites, image.favorites)
        binding.imageDownloads.text = getString(R.string.downloads, image.downloads)
    }
}