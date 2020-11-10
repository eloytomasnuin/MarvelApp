package com.ob.marvelapp

import android.app.Application
import com.ob.marvelapp.di.AppComponent
import com.ob.marvelapp.di.DaggerAppComponent

class MarvelApplication : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        component = getAppComponent()
        super.onCreate()
    }

    fun getAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }
}