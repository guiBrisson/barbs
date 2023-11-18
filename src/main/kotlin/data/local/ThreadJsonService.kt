package data.local

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import model.thread.Thread
import java.nio.file.Files
import java.nio.file.Paths

class ThreadJsonService {

    fun addThreadOnJsonFile(thread: Thread) {
        val threadList: MutableList<Thread> = readThreadsFromJsonFile().toMutableList()
        threadList.add(thread)
        writeThreadsOnJsonFile(threadList)
    }

    fun removeThreadFromJsonFile(id: String): Boolean {
        val threadList: MutableList<Thread> = readThreadsFromJsonFile().toMutableList()
        val successful = threadList.removeIf { it.id == id }
        writeThreadsOnJsonFile(threadList)
        return successful
    }

    fun readThreadListOnJsonFile(): List<Thread> = readThreadsFromJsonFile()

    private fun writeThreadsOnJsonFile(threads: List<Thread>) {
        try {
            createDataDir()
            val json = Json { prettyPrint = true }
            val jsonString = json.encodeToString(threads)

            File(JSON_PATH_NAME).writeText(jsonString)
        } catch (e: FileNotFoundException) {
            createDataDir()
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readThreadsFromJsonFile(): List<Thread> {
        val json = Json { ignoreUnknownKeys = true }

        return try {
            val file = File(JSON_PATH_NAME)
            val jsonString = file.readText()
            json.decodeFromString(jsonString)
        } catch (e: FileNotFoundException) {
            createDataDir()
            return emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    private fun createDataDir() {
        val directoryPath = "./.data"
        val path = Paths.get(directoryPath)

        // Directory exists
        if (Files.isDirectory(path)) return

        Files.createDirectory(path)
    }

    companion object {
        private const val JSON_PATH_NAME = ".data/threads.json"
    }
}