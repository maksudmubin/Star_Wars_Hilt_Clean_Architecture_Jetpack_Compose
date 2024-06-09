package com.mubin.starwars.domain.repo

import com.mubin.starwars.data.model.character.CharacterDetailsResponse
import com.mubin.starwars.data.model.character.CharacterListResponse
import com.mubin.starwars.data.model.planet.PlanetDetailsResponse
import com.mubin.starwars.data.model.planet.PlanetListResponse
import com.mubin.starwars.data.model.starship.StarshipDetailsResponse
import com.mubin.starwars.data.model.starship.StarshipListResponse

interface AppRepository {

    suspend fun getCharacters(url : String) : CharacterListResponse?

    suspend fun getCharacterDetails(url : String) : CharacterDetailsResponse?

    suspend fun getPlanets(url : String) : PlanetListResponse?

    suspend fun  getPlanetDetails(url : String) : PlanetDetailsResponse?

    suspend fun getStarships(url : String) : StarshipListResponse?

    suspend fun getStarshipDetails(url : String) : StarshipDetailsResponse?

}