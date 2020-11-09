package com.ob.marvelapp.di

import android.app.Application
import com.ob.marvelapp.ui.MainActivity
import com.ob.marvelapp.ui.screens.detail.di.HeroDetailSubComponent
import com.ob.marvelapp.ui.screens.list.di.HeroListSubComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppSubComponents::class,
        UseCasesModule::class,
        ViewModelFactoryModule::class]
)
interface AppComponent {

    val heroListSubComponent: HeroListSubComponent.Factory
    val heroDetailSubComponent: HeroDetailSubComponent.Factory

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Application): AppComponent
    }
}