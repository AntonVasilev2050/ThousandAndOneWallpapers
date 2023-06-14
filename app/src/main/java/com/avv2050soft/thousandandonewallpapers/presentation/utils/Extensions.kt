package com.avv2050soft.thousandandonewallpapers.presentation.utils

import android.app.Activity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.avv2050soft.thousandandonewallpapers.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun hideAppbarAndBottomView(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.nav_view)
    bottomNavigationView.visibility = View.GONE
}

fun showAppbarAndBottomView(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.nav_view)
    bottomNavigationView.visibility = View.VISIBLE
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
    if (bottomNavigationView.visibility == View.GONE){
        bottomNavigationView.visibility = View.VISIBLE
    }
}