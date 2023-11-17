package data.network.utils

import io.ktor.client.statement.HttpResponse


fun HttpResponse.isSuccessful(): Boolean {
    return status.value in 200..299
}