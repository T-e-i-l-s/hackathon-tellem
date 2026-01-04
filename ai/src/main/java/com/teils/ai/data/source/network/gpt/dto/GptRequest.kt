import kotlinx.serialization.Serializable


@Serializable
data class GptRequest(
    val modelUri: String,
    val completionOptions: CompletionOptions,
    val messages: List<Message>
)

@Serializable
data class CompletionOptions(
    val stream: Boolean,
    val temperature: Double,
    val maxTokens: String,
    val reasoningOptions: ReasoningOptions
)

@Serializable
data class ReasoningOptions(
    val mode: String
)

@Serializable
data class Message(
    val role: String,
    val text: String
)

@Serializable
data class GptResponse(
    val result: Result
)

@Serializable
data class Result(
    val alternatives: List<Alternative>,
    val usage: Usage? = null,
    val modelVersion: String? = null
)

@Serializable
data class Alternative(
    val message: Message,
    val status: String
)

@Serializable
data class Usage(
    val inputTextTokens: String,
    val completionTokens: String,
    val totalTokens: String
)
