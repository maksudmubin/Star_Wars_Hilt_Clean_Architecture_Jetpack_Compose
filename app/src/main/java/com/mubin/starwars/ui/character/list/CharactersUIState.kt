package com.mubin.starwars.ui.character.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mubin.starwars.data.model.character.CharacterListResponse

class CharactersUIState {

    var isLoading by mutableStateOf(false)
    var isMoreLoading by mutableStateOf(false)
    var showRootLayout by mutableStateOf(false)
    var totalCount by mutableIntStateOf(0)
    var nextPage by mutableStateOf("")

    var response: CharacterListResponse? by mutableStateOf(null)
    var characterList = mutableStateListOf<CharacterListResponse.Result?>()

}