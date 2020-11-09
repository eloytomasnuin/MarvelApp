package com.ob.marvelapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.marvelapp.di.PerFragment
import com.ob.marvelapp.ui.UIMapper
import com.ob.marvelapp.ui.model.UIHero
import com.ob.usecases.GetHeroes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@PerFragment
class HeroListViewModel @Inject constructor(
    private val getHeroes: GetHeroes,
    private val uiMapper: UIMapper,
    private val dispatcher: CoroutineDispatcher): ViewModel() {

    sealed class State {
        class Error(val failure: Failure) : State()
        class ShowItems(val heroList: List<UIHero>) : State()
        class IsLoading(val isLoading: Boolean) : State()
    }

    private var _heroesStateList = MutableLiveData<State>()

    val heroesStateList: LiveData<State>
        get() = _heroesStateList

    fun getHeroes(force: Boolean = false) {
        _heroesStateList.value = State.IsLoading(true)
        viewModelScope.launch(dispatcher) {
            getHeroes(GetHeroes.Params(force)).collect {
                _heroesStateList.value = State.IsLoading(false)
                it.fold(::handleFailure, ::handleResponse)
            }
        }
    }

    private fun handleResponse(heroList: List<Hero>) {
        _heroesStateList.value = State.ShowItems(uiMapper.convertHeroesToUIHeroes(heroList))
    }


    private fun handleFailure(failure: Failure) {
        _heroesStateList.value = State.Error(failure)
    }
}