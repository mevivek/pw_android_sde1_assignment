package com.example.pokemon.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.api.ApiService
import com.example.pokemon.models.PokemonListItem
import javax.inject.Inject

class PokemonPagingSource(private val apiService: ApiService) :
    PagingSource<Int, PokemonListItem>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = apiService.getPokemonList(nextPageNumber, params.loadSize)

            val prevKey = response.previous?.let {
                """[?&]offset=(\d+)""".toRegex()
                    .find(response.previous)?.groups?.get(1)?.value?.toInt()
            }
            val nextKey = response.next?.let {
                """[?&]offset=(\d+)""".toRegex()
                    .find(response.next)?.groups?.get(1)?.value?.toInt()
            }
            return LoadResult.Page(
                data = response.results.map {
                    PokemonListItem(
                        it.name,
                        it.url.split("/").dropLast(1).last().toInt()
                    )
                },
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = prevKey?.let { it + response.results.count() } ?: 0,
                itemsAfter = nextKey?.let { response.count - it } ?: 0
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}