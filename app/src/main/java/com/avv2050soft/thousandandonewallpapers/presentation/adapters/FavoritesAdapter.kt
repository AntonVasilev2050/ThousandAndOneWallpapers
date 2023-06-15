package com.avv2050soft.thousandandonewallpapers.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.thousandandonewallpapers.R
import com.avv2050soft.thousandandonewallpapers.databinding.ItemWallpaperBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FavoritesAdapter(
    private val onClickItem: (Hit) -> Unit,
    private val onClickLike: (Hit, position: Int) -> Unit,
    private val onClickDownload: (Hit) -> Unit,
    private val onClickShare: (Hit) -> Unit,
) : ListAdapter<Hit, FavoritesViewHolder> (DiffUtilFavorites()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {item ->
                Glide.with(imageViewWallpaperPreview.context)
                    .load(item.previewURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.baseline_error_24)
                    .into(imageViewWallpaperPreview)
                if(item.isFavorite){
                    imageViewLike.setImageResource(R.drawable.like_yes)
                }else{
                    imageViewLike.setImageResource(R.drawable.like_no)
                }
                root.setOnClickListener { onClickItem.invoke(item) }
                imageViewLike.setOnClickListener {
                    onClickLike.invoke(item, position)
                }
                imageViewDownload.setOnClickListener { onClickDownload.invoke(item) }
                imageViewShare.setOnClickListener { onClickShare.invoke(item) }
            }
        }
    }
}

class DiffUtilFavorites : DiffUtil.ItemCallback<Hit>() {
    override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean = oldItem == newItem
}

class FavoritesViewHolder(val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root)