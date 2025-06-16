package com.example.smartaid.chatbot

data class ChatRequest (
    val messages : List<ChatMessage>,
    val model: String,
    val stream: Boolean = false
)