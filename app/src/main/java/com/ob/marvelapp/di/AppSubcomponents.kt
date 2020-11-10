package com.ob.marvelapp.di

import com.ob.marvelapp.ui.screens.detail.di.HeroDetailSubComponent
import com.ob.marvelapp.ui.screens.list.di.HeroListSubComponent
import dagger.Module

@Module(subcomponents = [HeroListSubComponent::class, HeroDetailSubComponent::class])
interface AppSubComponents