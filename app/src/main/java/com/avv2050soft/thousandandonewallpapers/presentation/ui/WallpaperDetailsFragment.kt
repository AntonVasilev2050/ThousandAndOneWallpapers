package com.avv2050soft.thousandandonewallpapers.presentation.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

    private var wallpapersLargeImageUrl: String? = null

    private val requestDownloadPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                wallpapersLargeImageUrl?.let { downloadWalls(it) }
            } else {
                Snackbar.make(
                    requireView().findViewById(R.id.constraintLayoutWallpaperDetailsControls),
                    getString(R.string.permission_is_required_to_download_and_save_the_file),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.grant_permission)) {
                    wallpapersLargeImageUrl?.let { wallpapersLargeImageUrl ->
                        checkDownloadPermission(wallpapersLargeImageUrl)
                    }
                }.show()
            }
        }

    private fun checkDownloadPermission(wallpapersLargeImageUrl: String?) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestDownloadPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            downloadWalls(wallpapersLargeImageUrl)
        }
    }

    private fun downloadWalls(wallpapersLargeImageUrl: String?) {
        val request = DownloadManager.Request(Uri.parse(wallpapersLargeImageUrl))
            .setTitle("Wallpapers ${wallpapersLargeImageUrl?.substring(8..18)}")
            .setDescription("Downloading....")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setMimeType("image/*")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                File.separator + wallpapersLargeImageUrl?.substring(8..18) + ".jpg"
            )
        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wallpapersLargeImageUrl = arguments?.getString(WALLPAPERS_URL_KEY)
        hideAppbarAndBottomView(requireActivity())
        with(binding) {
            Glide.with(imageViewWallpaper)
                .load(wallpapersLargeImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.baseline_error_24)
                .into(imageViewWallpaper)
            imageViewSetupWalls.setOnClickListener { }
            imageViewLikeWalls.setOnClickListener { }
            imageViewDownloadWalls.setOnClickListener {
                checkDownloadPermission(
                    wallpapersLargeImageUrl
                )
            }
            imageViewShareWalls.setOnClickListener { shareWalls(wallpapersLargeImageUrl) }
        }
    }

    private fun shareWalls(wallpapersLargeImageUrl: String?) {
        val wallpapersLink = Uri.parse(wallpapersLargeImageUrl)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "a pixabay picture")
        intent.putExtra(Intent.EXTRA_TEXT, "$wallpapersLink")
        val chosenIntent = Intent.createChooser(intent, "Share the picture")
        startActivity(chosenIntent)
    }
}