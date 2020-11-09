package com.ob.marvelapp.ui.screens.detail.di

import com.ob.marvelapp.di.PerFragment
import com.ob.marvelapp.ui.screens.detail.HeroDetailFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [HeroDetailModule::class])
interface HeroDetailSubComponent {

    fun inject(fragment: HeroDetailFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HeroDetailSubComponent
    }
}