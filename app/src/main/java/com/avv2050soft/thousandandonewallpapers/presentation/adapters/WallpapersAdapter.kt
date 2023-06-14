package com.avv2050soft.thousandandonewallpapers.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.databinding.ItemWallpaperBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class WallpapersAdapter(
    private val onClickItem: (Hit) -> Unit,
    private val onClickLike: (Hit) -> Unit,
    private val onClickDownload: (Hit) -> Unit,
    private val onClickShare: (Hit) -> Unit,
) : PagingDataAdapter<Hit, WallpapersViewHolder>(DiffUtilWallpapers()) {
    override fun onBindViewHolder(holder: WallpapersViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {item ->
                Glide.with(imageViewWallpaperPreview)
                    .load(item.previewURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.baseline_error_24)
                    .into(imageViewWallpaperPreview)
                root.setOnClickListener { onClickItem.invoke(item) }
                imageViewLike.setOnClickListener { onClickLike.invoke(item) }
                imageViewDownload.setOnClickListener { onClickDownload.invoke(item) }
                imageViewShare.setOnClickListener { onClickShare.invoke(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersViewHolder {
        return WallpapersViewHolder(
            ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class DiffUtilWallpapers : DiffUtil.ItemCallback<Hit>() {
    override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean = oldItem == newItem
}

class WallpapersViewHolder(val binding: ItemWallpaperBinding) :
    RecyclerView.ViewHolder(binding.root)