package com.ob.marvelapp.ui.screens.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ob.marvelapp.databinding.FragmentDetailHeroBinding
import com.ob.marvelapp.extensions.application
import com.ob.marvelapp.extensions.loadImageUrl
import com.ob.marvelapp.ui.model.UIHero
import com.ob.marvelapp.ui.screens.adapters.HeroDetailListAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HeroDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailHeroBinding

    @Inject
    lateinit var viewModel: HeroDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val subComponent = application.component.heroDetailSubComponent.create()
        subComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHeroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureStates()
        val args: HeroDetailFragmentArgs by navArgs()
        viewModel.getHero(args.heroId)
    }

    private fun configureStates() {

        viewModel.heroState.observe(viewLifecycleOwner, { state ->

            when (state) {
                is HeroDetailViewModel.HeroDetailState.Error -> Log.e("ERROR", "Error loading hero")
                is HeroDetailViewModel.HeroDetailState.ShowItem -> setupView(state.item)
                is HeroDetailViewModel.HeroDetailState.IsLoading -> Log.d("LOADING", "Loading hero")
            }
        })
    }

    private fun setupView(hero: UIHero) {
        with(binding) {
            val storiesAdapter = HeroDetailListAdapter()
            val comicsAdapter = HeroDetailListAdapter()
            imgHero.loadImageUrl(hero.thumbnail)
            txtHeroName.text = hero.name
            txtHeroDescription.text = hero.description
            comicsRecyclerView.adapter = comicsAdapter
            storiesRecyclerView.adapter = storiesAdapter
            comicsAdapter.submitList(hero.comics)
            storiesAdapter.submitList(hero.stories)
        }
    }
}