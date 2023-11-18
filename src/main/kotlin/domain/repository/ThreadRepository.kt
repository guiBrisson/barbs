package domain.repository

import model.ResultOf
import model.thread.Thread

interface ThreadRepository {
    suspend fun createThread(): ResultOf<Thread>
    suspend fun listThreads(): ResultOf<List<Thread>>
    suspend fun deleteThread(id: String): ResultOf<Boolean>
}
