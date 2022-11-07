package com.example.models


data class User(
    val nickname: String,
    val login: String,
    val password: String
)

val loginPasswordStorage: MutableMap<String, String> = mutableMapOf<String, String>()


