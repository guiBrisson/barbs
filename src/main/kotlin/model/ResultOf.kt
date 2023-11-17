package model

sealed interface ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>
    object Loading: ResultOf<Nothing>
    data class Failure(val exception: Exception?): ResultOf<Nothing>
}
