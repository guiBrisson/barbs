package data.di

import data.network.KtorClient
import data.network.repository.AssistantsRepositoryImpl
import data.network.repository.ChatCompletionRepositoryImpl
import domain.repository.AssistantsRepository
import domain.repository.ChatCompletionRepository
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { KtorClient.client }
    single<ChatCompletionRepository> { ChatCompletionRepositoryImpl(get()) }
    single<AssistantsRepository> { AssistantsRepositoryImpl(get()) }
}