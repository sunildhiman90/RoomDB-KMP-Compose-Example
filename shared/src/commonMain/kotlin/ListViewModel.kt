import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val listRepository: ListRepository
) : ViewModel() {

    private val _repos = MutableStateFlow(ListUiState())
    val repos = _repos.asStateFlow()


    init {
        viewModelScope.launch {
            _repos.value = ListUiState(isLoading = true)
            listRepository.getKotlinTopRepos().collect {
                _repos.value = ListUiState(
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