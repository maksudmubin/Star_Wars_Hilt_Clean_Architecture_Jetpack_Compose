package com.mubin.starwars.data.api

import com.mubin.starwars.data.model.character.CharacterDetailsResponse
import com.mubin.starwars.data.model.character.CharacterListResponse
import com.mubin.starwars.data.model.planet.PlanetDetailsResponse
import com.mubin.starwars.data.model.planet.PlanetListResponse
import com.mubin.starwars.data.model.starship.StarshipDetailsResponse
import com.mubin.starwars.data.model.starship.StarshipListResponse
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

    @GET
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