package com.example.pokemon.api

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Item>
)

data class Item(val name: String, val url: String)
