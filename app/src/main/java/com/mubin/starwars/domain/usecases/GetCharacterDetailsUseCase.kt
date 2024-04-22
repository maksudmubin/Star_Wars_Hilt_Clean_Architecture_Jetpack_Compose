package com.mubin.starwars.domain.usecases

import com.mubin.starwars.base.utils.UseCase
import com.mubin.starwars.data.model.CharacterDetailsResponse
import com.mubin.starwars.data.repo.AppRepository
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(private val repository: AppRepository) : UseCase<CharacterDetailsResponse, String>() {
    override suspend fun run(params: String): CharacterDetailsResponse? {
        return repository.getCharacterDetails(params)
    }

}