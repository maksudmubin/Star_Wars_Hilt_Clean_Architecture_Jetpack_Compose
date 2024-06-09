package com.mubin.starwars.domain.usecases.planet

import com.mubin.starwars.base.utils.UseCase
import com.mubin.starwars.data.model.planet.PlanetListResponse
import com.mubin.starwars.domain.repo.AppRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(private val repository: AppRepository) : UseCase<PlanetListResponse, String>() {
    override suspend fun run(params: String): PlanetListResponse? {
        return repository.getPlanets(params)
    }


}