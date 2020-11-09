package com.ob.marvelapp.ui.list.di

import androidx.lifecycle.ViewModel
import com.ob.marvelapp.di.ViewModelKey
import com.ob.marvelapp.ui.list.HeroListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HeroListModule {

    @Binds
    @IntoMap
    @ViewModelKey(HeroListViewModel::class)
    abstract fun provideViewModel(viewModel: HeroListViewModel): ViewModel

}