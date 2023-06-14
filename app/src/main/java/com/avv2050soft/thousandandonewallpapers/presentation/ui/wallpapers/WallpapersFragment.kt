package com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.databinding.FragmentWallpapersBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.avv2050soft.thousandandonewallpapers.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.thousandandonewallpapers.presentation.adapters.WallpapersAdapter
import com.avv2050soft.thousandandonewallpapers.presentation.utils.toastString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WallpapersFragment : Fragment(R.layout.fragment_wallpapers) {

    private val binding by viewBinding(FragmentWallpapersBinding::bind)
    private val viewModel: WallpapersViewModel by viewModels()
    private val wallpapersAdapter = WallpapersAdapter(
        onClickItem = { hit: Hit -> onClickItem(hit) },
        onClickLike = { hit: Hit -> onClickLike(hit) },
        onClickDownload = { hit: Hit -> onClickDownload(hit) },
        onClickShare = { hit: Hit -> onClickShare(hit) }
    )

    private fun onClickItem(hit: Hit) {
        TODO("Not yet implemented")
    }

    private fun onClickLike(hit: Hit) {
        TODO("Not yet implemented")
    }

    private fun onClickDownload(hit: Hit) {
        TODO("Not yet implemented")
    }

    private fun onClickShare(hit: Hit) {
        val intent = Intent(Intent.ACTION_SEND).also {
            it.putExtra(Intent.EXTRA_TEXT, hit.largeImageURL)
            it.type = "text/plain"
        }
        try {
            requireContext().startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            toastString(e.message)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewWallpapers.adapter = wallpapersAdapter.withLoadStateFooter(CommonLoadStateAdapter())
        binding.swipeRefresh.setOnRefreshListener { wallpapersAdapter.refresh() }
        wallpapersAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pageWallpapers.onEach {
            wallpapersAdapter.submitData(it)

        }.launchIn(viewLifecycleOwner.lifecycleScope)

//        viewModel.pageWallpapers.launchAndCollectIn(viewLifecycleOwner){
//
//        }
    }
}