package com.example.pokemon.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.ui.Screen
import com.example.pokemon.components.ApiErrorWithRetry
import com.example.pokemon.components.RotatingBall
import com.example.pokemon.models.PokemonListItem

@Composable
fun PokemonListView(
    pokemonListViewModel: PokemonListViewModel = hiltViewModel(),
    navController: NavController
) {
    val pokemonListState = pokemonListViewModel.pokemonListState.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppBar(totalCount = pokemonListState.itemCount)
        }) { innerPadding ->
        when (pokemonListState.loadState.refresh) {
            is LoadState.Error -> ApiErrorWithRetry(
                error = (pokemonListState.loadState.refresh as LoadState.Error).error,
                retry = { pokemonListState.retry() })

            LoadState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                RotatingBall(modifier = Modifier.size(96.dp))
            }

            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                items(
                    pokemonListState.itemCount,
                    key = { pokemonListState[it]?.id ?: -it }) { index ->
                    val pokemon = pokemonListState[index]
                    if (pokemon == null)
                        Placeholder(index = index)
                    else PokemonItem(index = index, pokemon, goToDetails = { id ->
                        navController.navigate(
                            Screen.PokemonDetailsScreen.route
                                .replace("{id}", id.toString())
                                .replace("{totalCount}", pokemonListState.itemCount.toString())
                        )
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(totalCount: Int) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RotatingBall(modifier = Modifier.size(32.dp), speed = 1)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Pokemon List")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Total(${totalCount})",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        })
}

@Composable
fun PokemonItem(index: Int, pokemon: PokemonListItem, goToDetails: (id: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceContainer
            )
            .clickable {
                goToDetails(pokemon.id)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "#${pokemon.id}",
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun Placeholder(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceContainer
            )
            .height(32.dp)
    )
}

