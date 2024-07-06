import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val listRepository: ListRepository
) : ViewModel() {

    private val _users = MutableStateFlow(ListUiState())
    val users = _users.asStateFlow()


    init {
        viewModelScope.launch {
            _users.value = ListUiState(isLoading = true)
            listRepository.getKotlinTopRepos().collect {
                _users.value = ListUiState(
                    data = it,
                    isLoading = false
                )
            }
        }
    }
}

data class ListUiState(
    val data: List<GithubRepo>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null,
)