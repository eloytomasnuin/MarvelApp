package com.ob.marvelapp.ui.screens.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.marvelapp.ui.UIMapper
import com.ob.marvelapp.ui.model.UIHero
import com.ob.usecases.GetHeroById
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeroDetailViewModel @Inject constructor(
    private val getHero: GetHeroById,
    private val uiMapper: UIMapper
) : ViewModel() {

    sealed class HeroDetailState {
        class Error(val failure: Failure) : HeroDetailState()
        class ShowItem(val item: UIHero) : HeroDetailState()
        class IsLoading(val isLoading: Boolean) : HeroDetailState()
    }

    private var _heroDetailState = MutableLiveData<HeroDetailState>()

    val heroState: LiveData<HeroDetailState>
        get() = _heroDetailState

    fun getHero(id: Int) {

        viewModelScope.launch {
            getHero(GetHeroById.Params(id))
                .onStart { _heroDetailState.value = HeroDetailState.IsLoading(true) }
                .collect {
                    _heroDetailState.value = HeroDetailState.IsLoading(false)
                    it.fold(::handleFailure, ::handleResponse)
                }
        }
    }

    private fun handleResponse(hero: Hero) {
        _heroDetailState.value = HeroDetailState.ShowItem(uiMapper.convertHeroToUIHero(hero))
    }


    private fun handleFailure(failure: Failure) {
        _heroDetailState.value = HeroDetailState.Error(failure)
    }

}