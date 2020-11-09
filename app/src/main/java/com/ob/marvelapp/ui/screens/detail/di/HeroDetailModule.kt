package com.ob.marvelapp.ui.screens.detail.di

import androidx.lifecycle.ViewModel
import com.ob.marvelapp.di.ViewModelKey
import com.ob.marvelapp.ui.screens.detail.HeroDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HeroDetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(HeroDetailViewModel::class)
    abstract fun provideViewModel(viewModel: HeroDetailViewModel): ViewModel
}