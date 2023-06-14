package com.avv2050soft.thousandandonewallpapers.presentation.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.databinding.FragmentCategoriesBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.categories.Category
import com.avv2050soft.thousandandonewallpapers.presentation.adapters.CategoriesAdapter
import com.avv2050soft.thousandandonewallpapers.presentation.utils.toastString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val binding by viewBinding(FragmentCategoriesBinding::bind)
    private val viewModel by viewModels<CategoriesViewModel>()
    private val categoriesAdapter =
        CategoriesAdapter { category: Category -> onClickItem(category) }

    private fun onClickItem(category: Category) {
        toastString("Category: ${category.name}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCategories.adapter = categoriesAdapter
        categoriesAdapter.submitList(viewModel.loadCategories())
    }
}