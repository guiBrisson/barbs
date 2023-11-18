package domain.repository

import model.ResultOf
import model.thread.run.RunThread
import model.thread.run.step.RunStepList

interface RunThreadRepository {
    suspend fun createRun(assistantId: String, threadId: String): ResultOf<RunThread>
    suspend fun runStatus(threadId: String, runId: String): ResultOf<RunThread>
    suspend fun runStepList(threadId: String, runId: String): ResultOf<RunStepList>
}