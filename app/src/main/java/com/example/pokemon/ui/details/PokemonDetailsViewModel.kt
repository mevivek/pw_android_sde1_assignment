package com.example.pokemon.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.repositories.PokemonRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PokemonDetailsViewModel.Factory::class)
class PokemonDetailsViewModel @AssistedInject constructor(
    @Assisted("id") val id: Int,
    @Assisted("totalCount") val totalCount: Int,
    private val pokemonRepository: PokemonRepository
) :
    ViewModel() {
    private var currentId = id
    private val _pokemonDetails =
        MutableStateFlow<PokemonDetailsUiState>(PokemonDetailsUiState.Loading())
    val pokemonDetailsState: StateFlow<PokemonDetailsUiState> = _pokemonDetails.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            load()
        }
    }

    fun loadNext() {
        if (currentId < totalCount)
            currentId++
        load(PokemonDetailsUiState.Loading((_pokemonDetails.value as PokemonDetailsUiState.Loaded).pokemonDetails))
    }

    fun loadPrevious() {
        if (currentId > 1)
            currentId--
        load(PokemonDetailsUiState.Loading((_pokemonDetails.value as PokemonDetailsUiState.Loaded).pokemonDetails))
    }

    fun retry() {
        load()
    }

    private fun load(uiState: PokemonDetailsUiState = PokemonDetailsUiState.Loading()) {
        _pokemonDetails.value = uiState
        viewModelScope.launch {
            try {
                val pokemonDetails = pokemonRepository.getPokemonDetails(currentId)
                _pokemonDetails.value = PokemonDetailsUiState.Loaded(
                    pokemonDetails = pokemonDetails,
                    nextAvailable = currentId < totalCount,
                    previousAvailable = currentId > 1
                )
            } catch (e: Throwable) {
                _pokemonDetails.value = PokemonDetailsUiState.Error(e, (_pokemonDetails.value as? PokemonDetailsUiState.Loaded)?.pokemonDetails)
            }

        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("id") id: Int,
            @Assisted("totalCount") totalCount: Int
        ): PokemonDetailsViewModel
    }
}