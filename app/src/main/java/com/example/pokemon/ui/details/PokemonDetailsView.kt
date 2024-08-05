package com.example.pokemon.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.SubcomposeAsyncImage
import com.example.pokemon.R
import com.example.pokemon.models.PokemonDetailsResponse
import java.util.Locale

@Composable
fun PokemonDetailsView(
    uiState: PokemonDetailsUiState,
    innerPadding: PaddingValues,
    loadingBlur: Dp,
    goBack: () -> Unit,
    next: () -> Unit,
    previous: () -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> Column {
            ImageAndDetails(
                modifier = Modifier.weight(1f),
                uiState.details!!,
                innerPadding,
                blur = loadingBlur,
                goBack = { goBack() })

            NextPreviousView(
                uiState = uiState,
                innerPadding = innerPadding,
                next = next,
                previous = previous
            )
        }

        else -> Row {
            ImageAndDetails(
                modifier = Modifier.weight(1f),
                uiState.details!!,
                innerPadding,
                blur = loadingBlur,
                goBack = { goBack() })

            NextPreviousView(
                uiState = uiState,
                innerPadding = innerPadding,
                next = next,
                previous = previous
            )
        }
    }
}

@Composable
fun ImageAndDetails(
    modifier: Modifier,
    details: PokemonDetailsResponse,
    innerPadding: PaddingValues,
    blur: Dp,
    goBack: () -> Unit,
    windowSizeClass: WindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.COMPACT -> Column(modifier = modifier) {
            PokemonImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(radius = blur),
                url = details.sprites.otherSprites.officialArtwork?.frontDefault,
                innerPadding,
                goBack
            )
            Details(details, blur = blur)
        }

        else -> Row(modifier = modifier) {
            PokemonImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .blur(radius = blur),
                url = details.sprites.otherSprites.officialArtwork?.frontDefault,
                innerPadding,
                goBack
            )
            Details(details, blur = blur)
        }
    }
}

@Composable
fun PokemonImage(
    modifier: Modifier,
    url: String?,
    innerPadding: PaddingValues,
    goBack: () -> Unit
) {
    Box(modifier = Modifier.background(color = Color.LightGray.copy(alpha = 0.1f))) {
        SubcomposeAsyncImage(
            model = url,
            loading = {
                Image(
                    modifier = Modifier
                        .padding(48.dp)
                        .aspectRatio(1f)
                        .blur(radius = 16.dp),
                    painter = painterResource(id = R.drawable.pokemon_ball),
                    alpha = 0.5f,
                    contentDescription = ""
                )
            },
            contentDescription = "",
            modifier = modifier
        )
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 8.dp, top = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .blur(radius = 30.dp)
            )
            IconButton(
                onClick = goBack,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Go Back"
                )
            }
        }
    }
}

@Composable
fun Details(details: PokemonDetailsResponse, blur: Dp) {
    val verticalScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .blur(radius = blur)
            .verticalScroll(state = verticalScrollState)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        val title = buildAnnotatedString {
            withStyle(
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.5f
                    )
                ).toSpanStyle()
            ) {
                append("#" + details.id.toString())
            }
            append(" ")
            withStyle(style = MaterialTheme.typography.titleLarge.toSpanStyle()) {
                append(details.name.replaceFirstChar { it.uppercase() })
            }
        }
        Text(text = title)
        Spacer(modifier = Modifier.height(8.dp))
        DetailsCategoryTitle(title = "Basic Details")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Height: " + details.height.toString() + " Decimeter",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Weight: " + details.weight.toString() + " Hectometer",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(24.dp))
        DetailsCategoryTitle(title = "Types")
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            details.types.forEach {
                TypeChip(it.type.name)
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        DetailsCategoryTitle(title = "Abilities")
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = details.abilities.joinToString(", ") { it.ability.name })
    }
}

@Composable
fun NextPreviousView(
    uiState: PokemonDetailsUiState,
    innerPadding: PaddingValues,
    next: () -> Unit,
    previous: () -> Unit,
    windowSizeClass: WindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
) {

    when (windowSizeClass) {
        WindowWidthSizeClass.COMPACT -> Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NextPreviousButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 0.dp,
                    bottomStart = 32.dp,
                    bottomEnd = 0.dp
                ),
                icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                enabled = if (uiState is PokemonDetailsUiState.Loaded) uiState.previousAvailable else false,
                onClick = { previous() })
            VerticalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(IntrinsicSize.Max),
                color = Color.Transparent
            )
            NextPreviousButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 32.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 32.dp
                ),
                icon = Icons.AutoMirrored.Default.KeyboardArrowRight,
                enabled = if (uiState is PokemonDetailsUiState.Loaded) uiState.nextAvailable else false,
                onClick = { next() })
        }

        else -> Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NextPreviousButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                enabled = if (uiState is PokemonDetailsUiState.Loaded) uiState.previousAvailable else false,
                onClick = { previous() })
            HorizontalDivider(
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                color = Color.Transparent
            )
            NextPreviousButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                ),
                icon = Icons.AutoMirrored.Default.KeyboardArrowRight,
                enabled = if (uiState is PokemonDetailsUiState.Loaded) uiState.nextAvailable else false,
                onClick = { next() })

        }
    }
}

@Composable
fun NextPreviousButton(
    modifier: Modifier,
    shape: Shape,
    icon: ImageVector,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        enabled = enabled,
        onClick = { onClick() },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Previous Pokemon"
        )
    }
}

@Composable
fun DetailsCategoryTitle(title: String) {
    Text(text = title, style = MaterialTheme.typography.labelMedium)
}

@Composable
fun TypeChip(type: String) {

    fun getPokemonColors(type: String): Pair<Color, Color> {
        val color = when (type.lowercase(Locale.ROOT)) {
            "grass" -> Color(0xFF00FF00) // Green
            "fire" -> Color(0xFFFF0000) // Red
            "water" -> Color(0xFF0000FF) // Blue
            "normal" -> Color(0xFFA8A77A) // Normal color
            "flying" -> Color(0xFFA98FF3) // Flying color
            "fighting" -> Color(0xFFC22E28) // Fighting color
            "poison" -> Color(0xFFA33EA1) // Poison color
            "ground" -> Color(0xFFE2BF65) // Ground color
            "rock" -> Color(0xFFB6A136) // Rock color
            "bug" -> Color(0xFFA6B91A) // Bug color
            "ghost" -> Color(0xFF735797) // Ghost color
            "electric" -> Color(0xFFF7D02C) // Electric color
            "psychic" -> Color(0xFFF95587) // Psychic color
            "ice" -> Color(0xFF96D9D6) // Ice color
            "dragon" -> Color(0xFF6F35FC) // Dragon color
            "dark" -> Color(0xFF705746) // Dark color
            "steel" -> Color(0xFFB7B7CE) // Steel color
            "fairy" -> Color(0xFFD685AD) // Fairy color
            else -> Color.White
        }
        val textColor = when (type.lowercase(Locale.ROOT)) {
            "grass", "fire", "water", "flying", "fighting", "poison", "ground", "rock", "bug", "ghost", "electric", "psychic", "ice", "dragon", "dark", "steel", "fairy" -> Color.White
            else -> Color.Black
        }
        return Pair(color, textColor)
    }

    fun getPokemonTypeWithEmoji(type: String): String {
        return when (type.lowercase(Locale.ROOT)) {
            "normal" -> "😀 Normal"
            "grass" -> "🌼 Grass"
            "fire" -> "🔥 Fire"
            "water" -> "💧 Water"
            "flying" -> "✈️ Flying"
            "fighting" -> "💪 Fighting"
            "poison" -> "⚠️ Poison"
            "ground" -> "⛳️ Ground"
            "rock" -> "🗻 Rock"
            "bug" -> "🕷 Bug"
            "ghost" -> "👻 Ghost"
            "electric" -> "⚡️ Electric"
            "psychic" -> "🔮 Psychic"
            "ice" -> "❄️ Ice"
            "dragon" -> "🐲 Dragon"
            "dark" -> "🌑 Dark"
            "steel" -> "🔪 Steel"
            "fairy" -> "💟 Fairy"
            else -> "❓ $type"
        }
    }

    val color = getPokemonColors(type)
    Box(
        modifier = Modifier
            .background(color = color.first, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = getPokemonTypeWithEmoji(type),
            color = color.second,
            style = MaterialTheme.typography.labelMedium
        )
    }
}