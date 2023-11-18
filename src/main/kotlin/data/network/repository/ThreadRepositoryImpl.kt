package data.network.repository

import data.local.ThreadJsonService
import data.network.dto.ThreadDeletionResponse
import data.network.utils.isSuccessful
import domain.repository.ThreadRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.ResultOf
import model.thread.Thread

class ThreadRepositoryImpl(
    private val client: HttpClient,
    private val threadJsonService: ThreadJsonService,
) : ThreadRepository {

    override suspend fun createThread(): ResultOf<Thread> {
        val response = client.post("threads") {
            header("OpenAI-Beta", "assistants=v1")
        }

        return if (response.isSuccessful()) {
            val thread = response.body<Thread>()
            threadJsonService.addThreadOnJsonFile(thread)
            ResultOf.Success(thread)
        } else {
            ResultOf.Failure(null)
        }
    }

    override suspend fun listThreads(): ResultOf<List<Thread>> {
        return try {
            val list = threadJsonService.readThreadListOnJsonFile()
            ResultOf.Success(list)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultOf.Failure(e)
        }
    }

    override suspend fun deleteThread(id: String): ResultOf<Boolean> {
        val response = client.delete("threads/$id") {
            header("OpenAI-Beta", "assistants=v1")
        }

        return if (response.isSuccessful()) {
            val body = response.body<ThreadDeletionResponse>()
            threadJsonService.removeThreadFromJsonFile(id)
            ResultOf.Success(body.deleted)
        } else {
            ResultOf.Failure(null)
        }
    }
}
