package com.example.models

data class Chat(
    val messages: ArrayList<Message>
)

data class Message(
    val text: String,
    val user: User
)