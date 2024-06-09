package com.mubin.starwars.data.api

import com.mubin.starwars.data.model.character.CharacterDetailsResponse
import com.mubin.starwars.data.model.character.CharacterListResponse
import com.mubin.starwars.data.model.planet.PlanetDetailsResponse
import com.mubin.starwars.data.model.planet.PlanetListResponse
import com.mubin.starwars.data.model.starship.StarshipDetailsResponse
import com.mubin.starwars.data.model.starship.StarshipListResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class ApiService @Inject constructor(retrofit: Retrofit) : ApiEndpoints {

    private val api by lazy { retrofit.create(ApiEndpoints::class.java) }

    override fun getCharacters(url: String): Call<CharacterListResponse?> = api.getCharacters(url)

    override fun getCharacterDetails(url: String): Call<CharacterDetailsResponse?> = api.getCharacterDetails(url)

    override fun getPlanets(url: String): Call<PlanetListResponse?> = api.getPlanets(url)

    override fun getPlanetDetails(url: String): Call<PlanetDetailsResponse?> = api.getPlanetDetails(url)

    override fun getStarships(url: String): Call<StarshipListResponse?> = api.getStarships(url)

    override fun getStarshipDetails(url: String): Call<StarshipDetailsResponse?> = api.getStarshipDetails(url)
}