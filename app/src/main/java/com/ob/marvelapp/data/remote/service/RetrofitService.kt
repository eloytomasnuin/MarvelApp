package com.ob.marvelapp.data.remote.service

import com.ob.data.datasources.RemoteDataSource
import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.marvelapp.data.remote.JsonMapper
import com.ob.marvelapp.data.remote.RemoteMapper
import com.ob.marvelapp.data.remote.manager.ConnectionManager
import com.ob.marvelapp.data.remote.manager.NetworkManager
import org.json.JSONException
import org.json.JSONObject

class RetrofitService(
    private val retrofit: ApiService,
    private val serverMapper: RemoteMapper,
    private val jsonMapper: JsonMapper,
    private val connectivityManager: ConnectionManager
) : RemoteDataSource,
    NetworkManager by NetworkManager.NetworkImplementation() {

    object ConnectionError : Failure.CustomFailure()
    object ParsingError: Failure.CustomFailure()

    override suspend fun getHeroes(
        timeStamp: String,
        apiKey: String,
        hash: String
    ): Either<Failure, List<Hero>> {
        return when (connectivityManager.isConnected()) {
            true -> safeRequest(retrofit.getHeroes(timeStamp, apiKey, hash)) { listHero ->
                try {
                    val results = JSONObject(listHero.b)
                        .getJSONObject("data")
                        .getJSONArray("results")
                        .toString()
                    jsonMapper.getArray(results) { jsonArray ->
                        convertJsonToHeroes(jsonArray) { entity ->
                            serverMapper.convertEntityHeroToDomain(entity)
                        }
                    }
                } catch (e: JSONException) {
                    Either.Left(ParsingError)
                }
            }

            false -> Either.Left(ConnectionError)
        }
    }
}