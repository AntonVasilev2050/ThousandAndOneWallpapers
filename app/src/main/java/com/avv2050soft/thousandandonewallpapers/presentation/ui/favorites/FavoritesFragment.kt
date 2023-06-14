package com.avv2050soft.thousandandonewallpapers.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.data.objects.Favorites
import com.avv2050soft.thousandandonewallpapers.databinding.FragmentFavoritesBinding
import com.avv2050soft.thousandandonewallpapers.databinding.FragmentWallpapersBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.avv2050soft.thousandandonewallpapers.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.thousandandonewallpapers.presentation.adapters.WallpapersAdapter
import com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers.WALLPAPERS_IS_FAVORITE_KEY
import com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers.WALLPAPERS_URL_KEY
import com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers.WallpapersViewModel
import com.avv2050soft.thousandandonewallpapers.presentation.utils.checkDownloadPermission
import com.avv2050soft.thousandandonewallpapers.presentation.utils.downloadWalls
import com.avv2050soft.thousandandonewallpapers.presentation.utils.shareUrl
import com.avv2050soft.thousandandonewallpapers.presentation.utils.showAppbarAndBottomView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel: FavoritesViewModel by viewModels()

    private val favoritesAdapter = WallpapersAdapter(
        onClickItem = { hit: Hit -> onClickItem(hit) },
        onClickLike = { hit: Hit, position: Int -> onClickLike(hit, position) },
        onClickDownload = { hit: Hit -> onClickDownload(hit) },
        onClickShare = { hit: Hit -> onClickShare(hit) }
    )

    private var wallpapersLargeImageUrl: String? = null
    private val requestDownloadPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                wallpapersLargeImageUrl?.let { downloadWalls(it) }
            } else {
                Snackbar.make(
                    requireView().findViewById(R.id.constraintLayoutWallpapers),
                    getString(R.string.permission_is_required_to_download_and_save_the_file),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    private fun onClickItem(hit: Hit) {
        val bundle = Bundle()
        bundle.putString(WALLPAPERS_URL_KEY, hit.largeImageURL)
        bundle.putBoolean(WALLPAPERS_IS_FAVORITE_KEY, hit.isFavorite)
        findNavController().navigate(
            R.id.action_favoritesFragment_to_wallpaperDetailsFragment,
            bundle
        )
    }

    private fun onClickLike(hit: Hit, position : Int) {
        if (hit.isFavorite){
            Favorites.list.remove(hit)
            hit.isFavorite = false
        }else{
            Favorites.list.add(hit)
            hit.isFavorite = true
        }
        favoritesAdapter.notifyItemChanged(position)
    }

    private fun onClickDownload(hit: Hit) {
        wallpapersLargeImageUrl = hit.largeImageURL
        checkDownloadPermission(wallpapersLargeImageUrl, requestDownloadPermissionLauncher)
    }

    private fun onClickShare(hit: Hit) {
        shareUrl(hit.largeImageURL)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        if (bottomNavView?.visibility == View.GONE){
            showAppbarAndBottomView(requireActivity())
        }

        binding.recyclerViewFavorites.adapter =
            favoritesAdapter.withLoadStateFooter(CommonLoadStateAdapter())
        binding.swipeRefresh.setOnRefreshListener { favoritesAdapter.refresh() }
        favoritesAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }
}