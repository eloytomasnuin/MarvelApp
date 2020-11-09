package com.ob.marvelapp.data.remote

import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.marvelapp.data.remote.model.EntityHero
import com.squareup.moshi.Moshi
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonMapper(private val moshi: Moshi) {

    object JsonSyntaxException : Failure.CustomFailure()

    inline fun <T> getArray(json: String, body: JsonMapper.(JSONArray) -> T): T {
        return body(JSONArray(json))
    }

    private fun convertJsonToEntityHero(json: String): Either<Failure, EntityHero> {
        return try {
            val data =
                moshi.adapter(EntityHero::class.java).fromJson(json)

            data?.let {
                Either.Right(it)
            } ?: kotlin.run {
                Either.Left(JsonSyntaxException)
            }

        } catch (e: JSONException) {
            Either.Left(JsonSyntaxException)
        }
    }

    private fun convertJsonToHero(
        json: JSONObject,
        transform: (EntityHero) -> Either<Failure, Hero>
    ): Either<Failure, Hero> {

        return when (val result = convertJsonToEntityHero(json.toString())) {
            is Either.Left -> result
            is Either.Right -> transform(result.b)
        }
    }

    fun convertJsonToHeroes(
        json: JSONArray,
        transform: (EntityHero) -> Either<Failure, Hero>
    ): Either<Failure, List<Hero>> {

        val list = mutableListOf<Hero>()
        var result: Either<Failure, List<Hero>> = Either.Right(list)

        var index = 0
        var failure = false

        try {
            while (index < json.length() && !failure) {
                when (val convertion = convertJsonToHero(json.getJSONObject(index), transform)) {
                    is Either.Left -> {
                        failure = true
                        result = convertion
                    }
                    is Either.Right -> {
                        list.add(convertion.b)
                    }
                }
                index++
            }
        } catch (e: JSONException) {
            result = Either.Left(JsonSyntaxException)
        }
        return result
    }
}
