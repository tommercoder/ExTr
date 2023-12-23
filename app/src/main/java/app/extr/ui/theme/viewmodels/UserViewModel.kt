package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.CommonDataHolder
import app.extr.data.repositories.UserRepository
import app.extr.data.types.User
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//data class UsersUiState(
//    val isLoading: Boolean = false,
//    val users: List<User> = emptyList()
//)
sealed class UserUiEvent {
    data class UserCreated(val user: User) : UserUiEvent()
    data class UserNameChanged(val newName: String) : UserUiEvent()
}

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _user.asStateFlow()

    init {
        //todo: remove later
        val user = User(name = "Serhii")
        addUser(user)
        loadData()
    }

    fun onEvent(event: UserUiEvent) {
        when (event) {
            is UserUiEvent.UserCreated -> {
                addUser(event.user)
            }

            is UserUiEvent.UserNameChanged -> {
                updateUser(event.newName)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _user.value = UiState.Loading
            try {
                userRepository.getUser().collect { user ->
                    _user.value = UiState.Success(user)
                }
            } catch (e: Exception) {
                //todo: handle errors
            }
        }
    }

    private fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    private fun updateUser(newName: String) {
        viewModelScope.launch {
            val currentState = _user.value
            if (currentState is UiState.Success) {
                val updatedUser = currentState.data.copy(name = newName)
                userRepository.update(updatedUser)
            }
        }
    }

}