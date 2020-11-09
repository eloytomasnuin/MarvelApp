package com.ob.marvelapp.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ob.marvelapp.databinding.ItemHeroBinding
import com.ob.marvelapp.extensions.loadImageUrl
import com.ob.marvelapp.ui.model.UIHero

class HeroListAdapter : ListAdapter<UIHero, HeroListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<UIHero>() {

        override fun areItemsTheSame(oldItem: UIHero, newItem: UIHero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UIHero, newItem: UIHero): Boolean {
            return oldItem == newItem
        }

    }) {

    private lateinit var binding: ItemHeroBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: ItemHeroBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun onBind(item: UIHero) {
            itemBinding.txtHeroName.text = item.name
            itemBinding.imgHero.loadImageUrl(item.thumbnail)
        }
    }
}

