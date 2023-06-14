package com.avv2050soft.thousandandonewallpapers.presentation.utils

import android.Manifest
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File

fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastString(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.shareUrl(urlToShare: String?) {
    val uri = Uri.parse(urlToShare)
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, "a picture")
    intent.putExtra(Intent.EXTRA_TEXT, "$uri")
    val chosenIntent = Intent.createChooser(intent, "Share the picture")
    try {
        startActivity(chosenIntent)
    } catch (e: ActivityNotFoundException) {
        toastString(e.message)
    }
}

fun Fragment.checkDownloadPermission(
    wallpapersLargeImageUrl: String?,
    requestDownloadPermissionLauncher: ActivityResultLauncher<String>
) {
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

fun Fragment.downloadWalls(wallpapersLargeImageUrl: String?) {
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
