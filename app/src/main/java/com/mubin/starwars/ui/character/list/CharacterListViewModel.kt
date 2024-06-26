package com.mubin.starwars.ui.character.list

import androidx.lifecycle.ViewModel
import com.mubin.starwars.data.model.character.CharacterListResponse
import com.mubin.starwars.domain.usecases.character.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val useCase: GetCharactersUseCase) : ViewModel() {

    val uiState by lazy { CharactersUIState() }

    suspend fun getCharacters(url: String): CharacterListResponse? {

        return withContext(Dispatchers.IO) {
            useCase.run(url)
        }

    }

}