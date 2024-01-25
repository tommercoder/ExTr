package app.extr.utils.helpers

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val resourceId: Int) : UiState<Nothing>()
}
