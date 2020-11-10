package com.ob.marvelapp.di

import com.ob.data.MarvelRepository
import com.ob.usecases.GetHeroById
import com.ob.usecases.GetHeroes
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {
    @Singleton
    @Provides
    fun provideGetHeroes(marvelRepository: MarvelRepository) = GetHeroes(marvelRepository)


    @Singleton
    @Provides
    fun provideGetHero(marvelRepository: MarvelRepository) = GetHeroById(marvelRepository)
}