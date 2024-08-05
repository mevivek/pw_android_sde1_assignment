package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.ui.Screen
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.details.PokemonDetailsScreen
import com.example.pokemon.ui.list.PokemonListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PokemonListScreen.route) {
        composable(
            Screen.PokemonListScreen.route,
            popEnterTransition = { slideInHorizontally { fullWidth -> -fullWidth } }) {
            PokemonListView(navController = navController)
        }
        composable(
            Screen.PokemonDetailsScreen.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("totalCount") { type = NavType.IntType },
                navArgument("totalCount") { type = NavType.IntType }
            ),
            enterTransition = { slideInHorizontally { fullWidth -> fullWidth } },
            popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } }
        ) { backStack ->
            PokemonDetailsScreen(
                backStack.arguments?.getInt("id")!!,
                backStack.arguments?.getInt("totalCount")!!,
                navController = navController
            )
        }
    }
}