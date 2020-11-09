package com.ob.marvelapp.ui.screens.list.di

import android.os.Parcelable
import com.ob.marvelapp.di.PerFragment
import com.ob.marvelapp.ui.screens.list.HeroListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [HeroListModule::class])
interface HeroListSubComponent {

    fun inject(fragment: HeroListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance state: Parcelable?): HeroListSubComponent
    }

}