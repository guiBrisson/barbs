package data.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import utils.getLocalProperty

object KtorClient {
    private val OPENAI_API_KEY = getLocalProperty("openai.api.key").toString()

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(DefaultRequest) {
            url("https://api.openai.com/v1/")
            contentType(ContentType.Application.Json)
            bearerAuth(OPENAI_API_KEY)
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS

            // Sanitize sensitive headers to avoid their values appearing in the logs
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }
}
