package com.teils.ai.di

import com.teils.ai.data.repositories.GptRepository
import com.teils.ai.data.source.network.auth.YandexAuthApi
import com.teils.ai.data.source.network.gpt.GptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AiModule {
    @Provides
    @Singleton
    fun provideHttpClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    @Provides
    @Singleton
    fun provideGptApi(httpClient: HttpClient) = GptApi(httpClient)

    @Provides
    @Singleton
    fun provideYandexAuthApi(httpClient: HttpClient) = YandexAuthApi(httpClient)

    @Provides
    @Singleton
    fun provideGptRepository(gptApi: GptApi, yandexAuthApi: YandexAuthApi)
        = GptRepository(gptApi, yandexAuthApi)
}