package com.example.pokemon.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon.components.ApiErrorWithRetry
import com.example.pokemon.components.RotatingBall

@Composable
fun PokemonDetailsScreen(
    id: Int,
    totalCount: Int,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel<PokemonDetailsViewModel, PokemonDetailsViewModel.Factory> {
        it.create(id, totalCount = totalCount)
    },
    navController: NavController
) {
    val pokemonDetailsState by pokemonDetailsViewModel.pokemonDetailsState.collectAsState()

    Scaffold { innerPadding ->

        if (pokemonDetailsState.details != null) {
            val loadingBlur =
                if (pokemonDetailsState is PokemonDetailsUiState.Loading) 8.dp else 0.dp
            PokemonDetailsView(
                uiState = pokemonDetailsState,
                innerPadding = innerPadding,
                loadingBlur = loadingBlur,
                goBack = { navController.popBackStack() },
                next = { pokemonDetailsViewModel.loadNext() },
                previous = { pokemonDetailsViewModel.loadPrevious() })
        }

        if (pokemonDetailsState is PokemonDetailsUiState.Error) {
            ApiErrorWithRetry(
                error = (pokemonDetailsState as PokemonDetailsUiState.Error).error,
                retry = { pokemonDetailsViewModel.retry() })
        }

        if (pokemonDetailsState is PokemonDetailsUiState.Loading) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RotatingBall(modifier = Modifier.size(96.dp))
            }
        }
    }
}