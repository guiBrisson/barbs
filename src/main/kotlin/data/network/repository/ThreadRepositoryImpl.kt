package data.network.repository

import data.network.utils.isSuccessful
import domain.repository.ThreadRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.ResultOf
import model.thread.Thread

class ThreadRepositoryImpl(
    private val client: HttpClient,
) : ThreadRepository {

    override suspend fun createThread(): ResultOf<Thread> {
        val response = client.post("threads")
        return if (response.isSuccessful()) {
            val thread = response.body<Thread>()
            ResultOf.Success(thread)
        } else {
            ResultOf.Failure(null)
        }
    }
}