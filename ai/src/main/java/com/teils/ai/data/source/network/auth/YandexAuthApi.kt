package com.teils.ai.data.source.network.auth

import com.teils.ai.Secrets
import com.teils.ai.data.source.network.auth.dto.YandexAuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class YandexAuthApi @Inject constructor(
    private val httpClient: HttpClient
) {
    companion object {
        private const val URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens"
    }

    suspend fun getIamToken(oauthToken: String = Secrets.YANDEX_KEY): String {
        return (httpClient.post(URL) {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf("yandexPassportOauthToken" to oauthToken)
            )
        }.body() as YandexAuthResponse).iamToken
    }
}
