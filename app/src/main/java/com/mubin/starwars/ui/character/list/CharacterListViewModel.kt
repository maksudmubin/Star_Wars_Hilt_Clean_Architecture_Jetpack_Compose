package com.mubin.starwars.ui.character.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubin.starwars.data.model.CharacterListResponse
import com.mubin.starwars.domain.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(private val useCase: GetCharactersUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading = _isLoading

    private val _next = MutableLiveData<String?>()
    val next = _next

    private val _count = MutableLiveData<Int?>()
    val count = _count

    private val _characterList = MutableLiveData<List<CharacterListResponse.Result?>?>()
    val characterList: MutableLiveData<List<CharacterListResponse.Result?>?> = _characterList

    fun getCharacters(url: String = "") {
        try {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.Main) {
                val response = useCase(url)
                _count.value = response?.count
                _next.value = response?.next
                _characterList.value = response?.results
                _isLoading.value = false
            }
        } catch (e:Exception) {
            Log.d("errorFromAPi", "${e.message}")
            _isLoading.value = false
        }

    }

}