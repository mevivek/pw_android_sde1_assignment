package com.example.pokemon.ui.details

import com.example.pokemon.models.PokemonDetailsResponse

sealed class PokemonDetailsUiState(val details: PokemonDetailsResponse?) {
    data class Loading(val pokemonDetails: PokemonDetailsResponse? = null) :
        PokemonDetailsUiState(details = pokemonDetails)

    data class Loaded(
        val pokemonDetails: PokemonDetailsResponse,
        val nextAvailable: Boolean,
        val previousAvailable: Boolean
    ) : PokemonDetailsUiState(details = pokemonDetails)

    data class Error(val error: Throwable, val pokemonDetails: PokemonDetailsResponse? = null) :
        PokemonDetailsUiState(details = pokemonDetails)
}