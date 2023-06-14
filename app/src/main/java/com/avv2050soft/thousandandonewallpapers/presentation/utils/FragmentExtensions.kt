package com.avv2050soft.thousandandonewallpapers.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastString(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.shareUrl(urlToShare : String?){
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