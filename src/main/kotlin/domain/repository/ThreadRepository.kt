package domain.repository

import model.ResultOf
import model.thread.Thread

interface ThreadRepository {
    suspend fun createThread(): ResultOf<Thread>
}
