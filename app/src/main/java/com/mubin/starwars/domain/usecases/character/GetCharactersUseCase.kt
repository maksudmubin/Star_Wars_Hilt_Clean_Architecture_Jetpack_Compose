package com.mubin.starwars.domain.usecases.character

import com.mubin.starwars.base.utils.UseCase
import com.mubin.starwars.data.model.CharacterListResponse
import com.mubin.starwars.data.repo.AppRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: AppRepository) : UseCase<CharacterListResponse, String>() {
    override suspend fun run(params: String): CharacterListResponse? {
        return repository.getCharacters(params)
    }

}