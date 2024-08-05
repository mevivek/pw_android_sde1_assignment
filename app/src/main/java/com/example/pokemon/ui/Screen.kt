package com.example.pokemon.ui

sealed class Screen(val route: String) {
    data object PokemonListScreen : Screen("list")
    data object PokemonDetailsScreen : Screen("details/{id}?totalCount={totalCount}")
}