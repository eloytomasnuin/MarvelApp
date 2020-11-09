package com.ob.marvelapp.di

import com.ob.marvelapp.ui.list.di.HeroListSubComponent
import dagger.Module

@Module(subcomponents = [HeroListSubComponent::class])
interface AppSubComponents