package com.example.pokemon.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val height: Int, // Decimeters
    val weight: Int, // Hectograms
    val types: List<PokemonTypeSlot>,
    val abilities: List<PokemonAbilitySlot>,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>,

    val evolutionChain: EvolutionChain? = null
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

data class PokemonType(
    val name: String,
    val url: String
)

data class PokemonAbilitySlot(
    val ability: PokemonAbility,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int
)

data class PokemonAbility(
    val name: String,
    val url: String
)

data class PokemonSprites(
    @SerializedName("other") val otherSprites: OtherPokemonSprites
)

data class OtherPokemonSprites(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtworkSprites?
)

data class OfficialArtworkSprites(
    @SerializedName("front_default") val frontDefault: String
)

data class PokemonStat(
    @SerializedName("base_stat") val baseStat: Int,
    val effort: Int,
    val stat: PokemonStatInfo
)

data class PokemonStatInfo(
    val name: String,
    val url: String
)

// Evolution chain (structure depends on your API call)
data class EvolutionChain(val a: String)
