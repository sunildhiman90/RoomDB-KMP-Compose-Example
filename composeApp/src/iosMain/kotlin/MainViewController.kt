import androidx.compose.ui.window.ComposeUIViewController
import appDatabase.DBFactory

fun MainViewController() = ComposeUIViewController {
    App(DBFactory())
}
