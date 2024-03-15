package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.UserRepository
import app.extr.data.types.UiMode
import app.extr.data.types.User
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class UserUiEvent {
    data class UserCreated(val user: User) : UserUiEvent()
    data class UserNameChanged(val newName: String) : UserUiEvent()
    data class UiModeChanged(val newMode: UiMode) : UserUiEvent()
}

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = MutableStateFlow<UiState<User?>>(UiState.Loading)
    val uiState: StateFlow<UiState<User?>> = _user.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: UserUiEvent) {
        when (event) {
            is UserUiEvent.UserCreated -> {
                addUser(event.user)
            }

            is UserUiEvent.UserNameChanged -> {
                updateUsername(event.newName)
            }

            is UserUiEvent.UiModeChanged -> {
                updateUiMode(event.newMode)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _user.value = UiState.Loading
            try {
                userRepository.getUser().collectLatest { user ->
                    _user.value = UiState.Success(user)
                }
            } catch (e: Exception) {
                _user.value = UiState.Error(R.string.error_user_couldnt_load)
            }
        }
    }

    private fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    private fun updateUsername(newName: String) {
        viewModelScope.launch {
            val currentState = _user.value
            if (currentState is UiState.Success && currentState.data != null) {
                val updatedUser = currentState.data.copy(name = newName)
                userRepository.update(updatedUser)
            }
        }
    }

    private fun updateUiMode(uiMode: UiMode) {
        viewModelScope.launch {
            val currentState = _user.value
            if (currentState is UiState.Success && currentState.data != null) {
                val updatedUser = currentState.data.copy(uiMode = uiMode)
                userRepository.update(updatedUser)
            }
        }
    }

}