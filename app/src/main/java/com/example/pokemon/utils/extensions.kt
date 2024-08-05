package com.example.pokemon.utils

import java.net.UnknownHostException

fun Throwable.errorText(): String {
    return when (this) {
        is UnknownHostException -> "No internet connection"
        else -> this.message ?: "Unknown error"
    }
}