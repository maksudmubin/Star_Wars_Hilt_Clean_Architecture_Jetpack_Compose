package com.mubin.starwars.domain.repo

import com.mubin.starwars.data.api.ApiService
import com.mubin.starwars.data.model.CharacterDetailsResponse
import com.mubin.starwars.data.model.CharacterListResponse
import com.mubin.starwars.data.model.PlanetDetailsResponse
import com.mubin.starwars.data.model.PlanetListResponse
import com.mubin.starwars.data.model.StarshipDetailsResponse
import com.mubin.starwars.data.model.StarshipListResponse
import com.mubin.starwars.data.repo.AppRepository
import javax.inject.Inject

class AppRepositoryImpl
@Inject
constructor(private val apiService: ApiService) : AppRepository {

    override suspend fun getCharacters(url: String): CharacterListResponse? {


        val response = try {
            apiService.getCharacters(url).execute()
        } catch (e: Exception) {
            null
        }

        return if (response?.isSuccessful == true) {
            response.body()
        } else {
            null
        }

    }

    override suspend fun getCharacterDetails(url: String): CharacterDetailsResponse? {

        val response = apiService.getCharacterDetails(url).execute()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }

    }

    override suspend fun getPlanets(url: String): PlanetListResponse? {

        val response = try {
            apiService.getPlanets(url).execute()
        } catch (e: Exception) {
            null
        }

        return if (response?.isSuccessful == true) {
            response.body()
        } else {
            null
        }

    }

    override suspend fun getPlanetDetails(url: String): PlanetDetailsResponse? {

        val response = apiService.getPlanetDetails(url).execute()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }

    }

    override suspend fun getStarships(url: String): StarshipListResponse? {

        val response = apiService.getStarships(url).execute()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }

    }

    override suspend fun getStarshipDetails(url: String): StarshipDetailsResponse? {

        val response = apiService.getStarshipDetails(url).execute()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }

    }


}