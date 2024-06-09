package com.mubin.starwars.ui.planets

import androidx.lifecycle.ViewModel
import com.mubin.starwars.data.model.planet.PlanetListResponse
import com.mubin.starwars.domain.usecases.planet.GetPlanetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlanetsViewModel @Inject constructor(private val useCase: GetPlanetsUseCase) : ViewModel() {

    val uiState by lazy { PlanetsUIState() }
    suspend fun getPlanets(url: String) : PlanetListResponse? {

        return withContext(Dispatchers.IO) {
            useCase.run(url)
        }

    }



}

