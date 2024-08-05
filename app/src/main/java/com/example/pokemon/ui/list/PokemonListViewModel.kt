package com.example.pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.models.PokemonListItem
import com.example.pokemon.repositories.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {
    private val _pokemonListState =
        MutableStateFlow<PagingData<PokemonListItem>>(PagingData.empty())
    val pokemonListState: StateFlow<PagingData<PokemonListItem>> = _pokemonListState.asStateFlow()

    init {
        viewModelScope.launch {
            pokemonRepository.getPokemonList().cachedIn(viewModelScope).collect {
                _pokemonListState.value = it
            }
        }
    }
}