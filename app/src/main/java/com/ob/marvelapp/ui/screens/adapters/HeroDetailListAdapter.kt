package com.ob.marvelapp.ui.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ob.marvelapp.databinding.ItemHeroDetailBinding
import com.ob.marvelapp.ui.screens.adapters.HeroDetailListAdapter.ViewHolder

class HeroDetailListAdapter : ListAdapter<String, ViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }) {

    private lateinit var binding: ItemHeroDetailBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHeroDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: ItemHeroDetailBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun onBind(item: String) {
            itemBinding.txtGroupName.text = item
        }
    }
}

