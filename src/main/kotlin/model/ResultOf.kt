package model

sealed interface ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>
    data class Failure(val exception: Exception?): ResultOf<Nothing>
}
