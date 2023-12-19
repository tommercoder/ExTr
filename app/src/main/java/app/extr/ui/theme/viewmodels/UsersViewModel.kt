package app.extr.ui.theme.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.CommonDataHolder
import app.extr.data.repositories.UsersRepository
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
    data class UserSelected(val id: Int) : UserUiEvent()
    data class UserCreated(val user: User) : UserUiEvent()
}

class UsersViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _users = MutableStateFlow<UiState<List<User>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<User>>> = _users.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: UserUiEvent) {
        when (event) {
            is UserUiEvent.UserCreated -> {}
            is UserUiEvent.UserSelected -> {
                selectUser(event.id)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _users.value = UiState.Loading
            try {
                usersRepository.getAllUsers().collect { users ->
                    _users.value = UiState.Success(users)

                    //why is this triggered on every users update??
                    CommonDataHolder.currentUserId = users.firstOrNull { it.lastSelected }?.id // looks ugly
                }
            } catch (e: Exception) {
                //todo: handle errors
            }
        }
    }

    private fun selectUser(id: Int) {
        viewModelScope.launch {
            usersRepository.selectUser(id)
        }
    }

}