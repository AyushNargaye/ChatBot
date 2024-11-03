package com.example.chatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.ChatRoleEnum
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotVM : ViewModel() {
    val list by lazy {
        mutableStateListOf<ChatData>()
    }

    // Lazy initialization of the GenerativeModel with the API key
    private val genAI by lazy {
        Log.d("ChatBotVM", "API Key: $ApiKey") // Log the API Key
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = ApiKey
        )
    }

    fun sendMessage(message: String) = viewModelScope.launch {
        list.add(ChatData(message, ChatRoleEnum.USER.role)) // Add user message first
        try {
            val chat = genAI.startChat()

            // Ensure the correct role is set to 'user'
            val response = chat.sendMessage(
                content("user") { text(message) } // Explicitly set role as 'user'
            ).text

            if (!response.isNullOrEmpty()) {
                list.add(ChatData(response, ChatRoleEnum.MODEL.role))
            } else {
                Log.e("ChatBotVM", "Received null or empty response from AI")
                list.add(ChatData("Error: No response from AI", ChatRoleEnum.MODEL.role))
            }
        } catch (e: Exception) {
            Log.e("ChatBotVM", "Error sending message: ${e.localizedMessage}", e)
            list.add(ChatData("Error: Unable to connect to AI", ChatRoleEnum.MODEL.role))
        }
    }
}
