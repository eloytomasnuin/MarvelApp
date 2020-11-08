package com.ob.marvelapp.data.remote.service

import com.ob.domain.Constants
import retrofit2.Response

interface ApiService {

    @retrofit2.http.GET(Constants.Server.Hero.GET_HEROES)
    suspend fun getHeroes(): Response<String>

}