package com.teils.ai.data.source.network.gpt

import CompletionOptions
import GptRequest
import GptResponse
import Message
import ReasoningOptions
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GptApi @Inject constructor(
    private val httpClient: HttpClient
) {
    companion object {
        private const val URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    suspend fun sendPrompt(messages: List<Message>, iamToken: String): String {
        val request = GptRequest(
            modelUri = "gpt://b1gp6toap8jg7pfip1co/yandexgpt-lite",
            completionOptions = CompletionOptions(
                stream = false,
                temperature = 0.6,
                maxTokens = "2000",
                reasoningOptions = ReasoningOptions("DISABLED")
            ),
            messages = messages
        )

        val jsonBody = json.encodeToString(GptRequest.serializer(), request)

        val responseText: String = withContext(Dispatchers.IO) {
            httpClient.post(URL) {
                contentType(ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $iamToken")
                }
                setBody(jsonBody)
            }.bodyAsText()
        }

        val response = json.decodeFromString(GptResponse.serializer(), responseText)

        return response.result.alternatives.firstOrNull()?.message?.text
            ?: "⚠️ Ваш лимит на запросы исчерпан"
    }
}
