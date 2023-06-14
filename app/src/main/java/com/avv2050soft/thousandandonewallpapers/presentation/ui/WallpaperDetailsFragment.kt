package com.avv2050soft.thousandandonewallpapers.presentation.ui

import android.Manifest
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import com.avv2050soft.thousandandonewallpapers.databinding.DialogConfirmWallpaperSetupBinding
import com.avv2050soft.thousandandonewallpapers.databinding.FragmentWallpaperDetailsBinding
import com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers.WALLPAPERS_URL_KEY
import com.avv2050soft.thousandandonewallpapers.presentation.utils.hideAppbarAndBottomView
import com.avv2050soft.thousandandonewallpapers.presentation.utils.toastString
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class WallpaperDetailsFragment : Fragment(R.layout.fragment_wallpaper_details) {
    private val binding by viewBinding(FragmentWallpaperDetailsBinding::bind)
    private val viewModel by viewModels<WallpaperDetailsViewModel>()

    private var wallpapersLargeImageUrl: String? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                applyWallpaper(wallpapersLargeImageUrl)
            } else {
                toastString(getString(R.string.permission_denied))
            }
        }

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
            imageViewSetupWalls.setOnClickListener { conformWallpapersSetup() }
            imageViewLikeWalls.setOnClickListener { }
            imageViewDownloadWalls.setOnClickListener {
                checkDownloadPermission(wallpapersLargeImageUrl)
            }
            imageViewShareWalls.setOnClickListener { shareWalls(wallpapersLargeImageUrl) }
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

    private fun conformWallpapersSetup() {
        val confirmDialog = BottomSheetDialog(requireContext())
        val bindingDialog = DialogConfirmWallpaperSetupBinding.inflate(layoutInflater)
        confirmDialog.setContentView(bindingDialog.root)
        bindingDialog.buttonNo.setOnClickListener { confirmDialog.dismiss() }
        bindingDialog.buttonYes.setOnClickListener {
            setWallpaper()
            confirmDialog.dismiss()
        }
        confirmDialog.show()
    }

    private fun setWallpaper() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SET_WALLPAPER
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.SET_WALLPAPER)
        } else {
            applyWallpaper(wallpapersLargeImageUrl)
        }
    }

    private fun applyWallpaper(wallpapersLargeImageUrl: String?) {
        val wallpaperManager = WallpaperManager.getInstance(requireContext())

        Glide.with(requireContext())
            .asBitmap()
            .load(wallpapersLargeImageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    try {
                        wallpaperManager.setBitmap(resource)
                        toastString("Wallpaper was set up successfully")
                    } catch (e: IOException) {
                        toastString("Wallpaper setup failed: ${e.message}")
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Optional: Perform any additional cleanup here
                }
            })
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