package app.extr.ui.theme.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class UsersViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _users = MutableStateFlow<UiState<List<User>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<User>>> = _users.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _users.value = UiState.Loading
            try {
                usersRepository.getAllUsers().collect {
                    _users.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                //todo: handle errors
            }
        }
    }

}