package com.mubin.starwars.data.api

import com.mubin.starwars.data.model.CharacterDetailsResponse
import com.mubin.starwars.data.model.CharacterListResponse
import com.mubin.starwars.data.model.PlanetDetailsResponse
import com.mubin.starwars.data.model.PlanetListResponse
import com.mubin.starwars.data.model.StarshipDetailsResponse
import com.mubin.starwars.data.model.StarshipListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiEndpoints {

    @GET
    fun getCharacters(
        @Url url: String
    ) : Call<CharacterListResponse?>

    @GET
    fun getCharacterDetails(
        @Url url: String
    ) : Call<CharacterDetailsResponse?>

    @GET("planets")
    fun getPlanets(
        @Url url: String
    ) : Call<PlanetListResponse?>

    @GET
    fun getPlanetDetails(
        @Url url: String
    ) : Call<PlanetDetailsResponse?>

    @GET("starships")
    fun getStarships(
        @Url url: String
    ) : Call<StarshipListResponse?>

    @GET
    fun getStarshipDetails(
        @Url url: String
    ) : Call<StarshipDetailsResponse?>

}