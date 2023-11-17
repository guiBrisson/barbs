package domain.repository

import model.ResultOf
import model.assistant.AssistantList

interface AssistantsRepository {
    suspend fun listAssistants(): ResultOf<AssistantList>
}
