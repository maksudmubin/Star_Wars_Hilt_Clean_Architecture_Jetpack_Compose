package com.mubin.starwars.ui.planets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mubin.starwars.data.model.PlanetListResponse

class PlanetsUIState {

    var isLoading by mutableStateOf(false)
    var showRootLayout by mutableStateOf(false)

    var response: PlanetListResponse? by mutableStateOf(null)
    var planetList = mutableStateListOf<PlanetListResponse.Result?>()

}