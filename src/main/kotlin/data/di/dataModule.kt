package data.di

import data.local.ThreadJsonService
import data.network.KtorClient
import data.network.repository.*
import domain.repository.*
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
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<RunThreadRepository> { RunThreadRepositoryImpl(get()) }
}

val dataModule = arrayOf(localModule, networkModule)
