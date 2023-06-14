package com.avv2050soft.thousandandonewallpapers.presentation.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.databinding.FragmentWallpaperDetailsBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers.WALLPAPERS_URL_KEY
import com.avv2050soft.thousandandonewallpapers.presentation.utils.hideAppbarAndBottomView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class WallpaperDetailsFragment : Fragment(R.layout.fragment_wallpaper_details) {
    private val binding by viewBinding(FragmentWallpaperDetailsBinding::bind)
    private val viewModel by viewModels<WallpaperDetailsViewModel>()

    private var wallpapersDetails: Hit? = null

    private val requestDownloadPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                wallpapersDetails?.let { downloadPhoto(it) }
            } else {
                Snackbar.make(
                    requireView().findViewById(R.id.constraintLayoutWallpaperDetailsControls),
                    getString(R.string.permission_is_required_to_download_and_save_the_file),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.grant_permission)) {
                        wallpapersDetails?.let { wallpapersDetails -> checkDownloadPermission(wallpapersDetails) }
                    }
                    .show()
            }
        }

    private fun checkDownloadPermission(wallpapersDetails: Hit) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestDownloadPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            downloadPhoto(wallpapersDetails)
        }
    }

    private fun downloadPhoto(wallpapersDetails: Hit) {
        val url = wallpapersDetails.largeImageURL
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Wallpapers ${wallpapersDetails.tags}")
            .setDescription("Downloading....")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setMimeType("image/*")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                File.separator + wallpapersDetails.id + ".jpg"
            )
        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wallpapersLargeImageUrl = arguments?.getString(WALLPAPERS_URL_KEY)
        hideAppbarAndBottomView(requireActivity())
        with(binding){
            Glide.with(imageViewWallpaper)
                .load(wallpapersLargeImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.baseline_error_24)
                .into(imageViewWallpaper)
            imageViewSetupWalls.setOnClickListener { }
            imageViewLikeWalls.setOnClickListener { }
            imageViewDownloadWalls.setOnClickListener {  }
            imageViewShareWalls.setOnClickListener {  }
        }

    }
}