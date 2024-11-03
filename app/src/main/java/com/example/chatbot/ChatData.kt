package com.example.chatbot

class ChatData (
    val message : String,
    val role : String
)
enum class ChatRoleEnum(val role : String){
    USER("User"),
    MODEL("Model")
}