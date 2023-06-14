package com.avv2050soft.thousandandonewallpapers.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.thousandandonewallpapers.databinding.ItemCategoryBinding
import com.avv2050soft.thousandandonewallpapers.domain.models.categories.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CategoriesAdapter(
    private val onClickItem: (Category) -> Unit
) : ListAdapter<Category, CategoriesViewHolder>(DiffUtilCategories()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            textViewCategoryName.text = item.name
            val pictureSize = root.context.resources.displayMetrics.widthPixels / 4
            val requestOptions = RequestOptions()
                .override(pictureSize, pictureSize)
                .optionalFitCenter()
            Glide.with(imageViewCategoryPicture.context)
                .load(item.picture)
                .apply(requestOptions)
                .into(imageViewCategoryPicture)
            root.setOnClickListener { onClickItem.invoke(item) }
        }
    }


}

class DiffUtilCategories : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem == newItem
}

class CategoriesViewHolder(val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root)