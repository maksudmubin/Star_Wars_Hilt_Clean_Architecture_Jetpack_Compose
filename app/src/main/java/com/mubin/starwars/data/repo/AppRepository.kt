package com.mubin.starwars.data.repo

import com.mubin.starwars.data.model.CharacterDetailsResponse
import com.mubin.starwars.data.model.CharacterListResponse
import com.mubin.starwars.data.model.PlanetDetailsResponse
import com.mubin.starwars.data.model.PlanetListResponse
import com.mubin.starwars.data.model.StarshipDetailsResponse
import com.mubin.starwars.data.model.StarshipListResponse

interface AppRepository {

    suspend fun getCharacters(url : String) : CharacterListResponse?

    suspend fun getCharacterDetails(url : String) : CharacterDetailsResponse?

    suspend fun getPlanets(url : String) : PlanetListResponse?

    suspend fun  getPlanetDetails(url : String) : PlanetDetailsResponse?

    suspend fun getStarships(url : String) : StarshipListResponse?

    suspend fun getStarshipDetails(url : String) : StarshipDetailsResponse?

}