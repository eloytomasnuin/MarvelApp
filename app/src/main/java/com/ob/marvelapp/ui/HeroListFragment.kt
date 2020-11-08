package com.ob.marvelapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ob.marvelapp.databinding.FragmentListHeroBinding
import com.ob.marvelapp.ui.adapters.HeroListAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HeroListFragment : Fragment() {

    private lateinit var binding: FragmentListHeroBinding
    private lateinit var adapter: HeroListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListHeroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()
    }

    private fun configureUI() {
        with(binding) {
            adapter = HeroListAdapter()
            val layoutManager = LinearLayoutManager(context)

            binding.heroRecyclerView.layoutManager = layoutManager
            binding.heroRecyclerView.adapter = adapter
        }
    }
}