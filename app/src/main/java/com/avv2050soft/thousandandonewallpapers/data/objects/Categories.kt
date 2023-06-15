package com.avv2050soft.thousandandonewallpapers.data.objects

import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.domain.models.categories.Category

object Categories {
    val list = listOf<Category>(
        Category("Car", R.drawable.category_car),
        Category("Cat", R.drawable.category_cat),
        Category("Dog", R.drawable.category_dog),
        Category("Flower", R.drawable.category_flower),
        Category("House", R.drawable.category_house),
        Category("New Year", R.drawable.category_new_year),
        Category("Office", R.drawable.category_office),
        Category("Season", R.drawable.category_season),
        Category("All", R.drawable.category_all)
    )
}