package data.network.repository

import data.network.utils.isSuccessful
import domain.repository.AssistantsRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.ResultOf
import model.assistant.AssistantList
import java.lang.Exception

class AssistantsRepositoryImpl(
    private val client: HttpClient,
) : AssistantsRepository {

    override suspend fun listAssistants(): ResultOf<AssistantList> {
        val response = client.get("assistants") {
            header("OpenAI-Beta", "assistants=v1")
            parameter("order", "desc")
            parameter("limit", "20")
        }

        return if (response.isSuccessful()) {
            val assistantList = response.body<AssistantList>()
            if (assistantList.data.isEmpty()) {
                return ResultOf.Failure(Exception("Empty list"))
            }

            ResultOf.Success(assistantList)
        } else {
            ResultOf.Failure(null)
        }
    }
}
