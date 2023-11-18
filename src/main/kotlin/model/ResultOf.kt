package model

sealed interface ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>
    data class Failure(val exception: Exception?): ResultOf<Nothing>

    fun isSuccessful() : Boolean {
        return when(this) {
            is Success -> true
            else -> false
        }
    }
}
