package data.di

import data.local.ThreadJsonService
import data.network.KtorClient
import data.network.repository.AssistantsRepositoryImpl
import data.network.repository.ChatCompletionRepositoryImpl
import data.network.repository.ThreadRepositoryImpl
import domain.repository.AssistantsRepository
import domain.repository.ChatCompletionRepository
import domain.repository.ThreadRepository
import io.ktor.client.*
import org.koin.dsl.module

val localModule = module {
    single { ThreadJsonService() }
}

val networkModule = module {
    single<HttpClient> { KtorClient.client }
    single<ChatCompletionRepository> { ChatCompletionRepositoryImpl(get()) }
    single<AssistantsRepository> { AssistantsRepositoryImpl(get()) }
    single<ThreadRepository> { ThreadRepositoryImpl(get(), get()) }
}

val dataModule = arrayOf(localModule, networkModule)
