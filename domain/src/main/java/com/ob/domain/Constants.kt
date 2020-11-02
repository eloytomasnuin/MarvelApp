package com.ob.domain

object Constants{

    object Server {
        const val API_KEY = "ff1bbafd775a6d0209d677f348c22d6b"
        const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        const val LIMIT = "20"

        object Hero {
            const val GET_HEROES = "characters?limit=$LIMIT&apikey=$API_KEY"
        }

        object OkHTTPConfig {
            const val timeOut = 15000L
        }
    }
}