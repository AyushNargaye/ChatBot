package com.example.chatbot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import com.example.chatbot.components.ChatFooter
import com.example.chatbot.components.ChatHeader
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatbot.components.ChatList

@Composable
fun ChatBot(viewModel: ChatBotVM = viewModel()) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ChatHeader()

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.list.isEmpty()) {
                Text(text = "No Chat Available")
            } else {
                ChatList(list = viewModel.list)
            }
        }

        ChatFooter { message ->
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
            }
        }
    }
}
