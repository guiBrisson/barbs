package data.network.repository

import data.network.utils.isSuccessful
import domain.repository.RunThreadRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import model.ResultOf
import model.thread.run.RunThread
import model.thread.run.step.RunStepList

class RunThreadRepositoryImpl(
    private val client: HttpClient,
): RunThreadRepository {

    override suspend fun runThread(assistantId: String, threadId: String): ResultOf<RunThread> {
        val body = mapOf("assistant_id" to assistantId)
        val response = client.post("threads/$threadId/runs") {
            header("OpenAI-Beta", "assistants=v1")
            setBody(body)
        }

        return if (response.isSuccessful()) {
            val responseBody = response.body<RunThread>()
            ResultOf.Success(responseBody)
        } else {
            println(response.bodyAsText())
            ResultOf.Failure(null)
        }
    }

    override suspend fun runStatus(threadId: String, runId: String): ResultOf<RunThread> {
        val response = client.post("threads/$threadId/runs/$runId") {
            header("OpenAI-Beta", "assistants=v1")
        }

        return if (response.isSuccessful()) {
            val responseBody = response.body<RunThread>()
            ResultOf.Success(responseBody)
        } else {
            println(response.bodyAsText())
            ResultOf.Failure(null)
        }
    }

    override suspend fun runStepList(threadId: String, runId: String): ResultOf<RunStepList> {
        val response = client.get("threads/$threadId/runs/$runId/steps") {
            header("OpenAI-Beta", "assistants=v1")
        }

        return if (response.isSuccessful()) {
            val body = response.body<RunStepList>()
            ResultOf.Success(body)
        } else {
            println(response.bodyAsText())
            ResultOf.Failure(null)
        }
    }
}
