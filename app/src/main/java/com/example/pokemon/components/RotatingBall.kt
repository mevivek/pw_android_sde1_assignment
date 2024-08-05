package com.example.pokemon.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.R

@Composable
fun RotatingBall(modifier: Modifier, speed: Int = 10) {
    val rotation by rememberInfiniteTransition(label = "pokemon ball").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000 / speed, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Image(
        modifier = modifier.rotate(rotation),
        painter = painterResource(id = R.drawable.pokemon_ball),
        contentDescription = ""
    )
}

@Composable
@Preview
fun LoadingViewPreview() {
    RotatingBall(modifier = Modifier.size(64.dp), speed = 1)
}