package com.avv2050soft.thousandandonewallpapers.presentation.utils

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.avv2050soft.thousandandonewallpapers.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun hideAppbarAndBottomView(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).isGone = true
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.nav_view)
    bottomNavigationView.isGone = true
}

fun showAppbarAndBottomView(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).isGone = false
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.nav_view)
    bottomNavigationView.isGone = false
}

fun hideAppbar(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).isGone = true
}

fun showAppbar(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).isGone = false
}

fun hideABottomView(requireActivity: Activity) {
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.nav_view)
    bottomNavigationView.isGone = true
}

fun showBottomView(requireActivity: Activity) {
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.nav_view)
    bottomNavigationView.isGone = false
}