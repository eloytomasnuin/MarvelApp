package com.ob.marvelapp.ui.screens.list

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ob.marvelapp.databinding.FragmentListHeroBinding
import com.ob.marvelapp.extensions.application
import com.ob.marvelapp.ui.Events.EventObserver
import com.ob.marvelapp.ui.model.UIHero
import com.ob.marvelapp.ui.screens.adapters.HeroListAdapter
import com.ob.marvelapp.ui.screens.list.HeroListViewModel.NavigationEvent
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HeroListFragment : Fragment() {

    @Inject
    lateinit var viewModel: HeroListViewModel

    private lateinit var binding: FragmentListHeroBinding
    private lateinit var adapter: HeroListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var state: Parcelable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val subComponent = application.component.heroListSubComponent.create(state)
        subComponent.inject(this)
    }

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

        setupView()
        configureStates()
        prepareForEvents()
    }

    override fun onPause() {
        super.onPause()
        viewModel.state = layoutManager.onSaveInstanceState()
    }

    private fun configureStates() {

        viewModel.heroesStateList.observe(viewLifecycleOwner, { state ->

            when (state) {
                is HeroListViewModel.State.Error -> Log.e("Error", state.toString())
                is HeroListViewModel.State.ShowItems -> adapter.submitList(state.heroList)
                is HeroListViewModel.State.IsLoading -> binding.progress.isVisible = state.isLoading
            }
        })
    }

    private fun prepareForEvents() {
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                is NavigationEvent.ToHeroDetail -> {
                    val extras = FragmentNavigatorExtras()
                    val action = HeroListFragmentDirections.actionToDetail(event.arg)
                    findNavController().navigate(action, extras)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHeroes()
    }

    private fun setupView() {
        adapter = HeroListAdapter(object : HeroListAdapter.OnItemClickListener {
            override fun onItemClick(uiHero: UIHero) {
                viewModel.navigateToDetail(NavigationEvent.ToHeroDetail(uiHero.id))
            }
        })
        layoutManager = LinearLayoutManager(context)
        binding.heroRecyclerView.layoutManager = layoutManager
        binding.heroRecyclerView.adapter = adapter

        viewModel.state?.let { layoutManager.onRestoreInstanceState(it) }
    }
}