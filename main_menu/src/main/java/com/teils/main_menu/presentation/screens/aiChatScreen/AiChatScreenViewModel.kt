package com.teils.main_menu.presentation.screens.aiChatScreen

import Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teils.ai.data.repositories.GptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private var messagesShownMemory = 0

@HiltViewModel
class AiChatScreenViewModel @Inject constructor(
    private val gptRepository: GptRepository
) : ViewModel() {
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _feedback = MutableStateFlow(emptyList<String>())
    val feedback: StateFlow<List<String>> = _feedback

    private val _sendAvailable = MutableStateFlow(true)
    val sendAvailable: StateFlow<Boolean> = _sendAvailable

    private val _messages = MutableStateFlow(emptyList<Message>())
    val messages: StateFlow<List<Message>> = _messages

    private val _messagesShown = MutableStateFlow(messagesShownMemory)
    val messagesShown: StateFlow<Int> = _messagesShown

    init {
        _messages.value = gptRepository.messages.toMutableList().apply {
            removeAt(0)
        }
        _feedback.value = gptRepository.feedback
    }

    fun sendMessage() {
        viewModelScope.launch {
            val text = inputText.value
            _inputText.value = ""

            async {
                gptRepository.sendMessage(text)
                    .collect { updatedMessages ->
                        _messages.value = updatedMessages
                        _sendAvailable.value = updatedMessages.last().role == "assistant"
                    }
            }

            async {
                if (messages.value.size > 2) {
                    _feedback.value = gptRepository.getFeedback(
                        messages.value[messages.value.size - 2].text,
                        messages.value[messages.value.size - 1].text
                    )
                }
            }
        }
    }

    fun updateInputText(text: String) {
        viewModelScope.launch {
            _inputText.value = text
        }
    }

    fun addShownMessage(count: Int = messagesShown.value + 1) {
        _messagesShown.value = count
        messagesShownMemory = count
    }
}