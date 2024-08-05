package com.example.pokemon.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.api.ApiService
import com.example.pokemon.models.PokemonDetailsResponse
import com.example.pokemon.models.PokemonListItem
import com.example.pokemon.sources.PokemonPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val apiService: ApiService) {
    fun getPokemonList(): Flow<PagingData<PokemonListItem>> {
        return Pager(
            PagingConfig(
                initialLoadSize = 20,
                pageSize = 10,
                enablePlaceholders = true,
            )
        ) {
            PokemonPagingSource(
                apiService
            )
        }.flow
    }

    suspend fun getPokemonDetails(id: Int): PokemonDetailsResponse {
        return apiService.getPokemonDetails(id)
    }
}