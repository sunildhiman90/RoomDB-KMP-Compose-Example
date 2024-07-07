import androidx.compose.ui.window.ComposeUIViewController
import appDatabase.DBFactory

fun MainViewController() = ComposeUIViewController {
    val db = DBFactory().createDatabase()
    App(db)
}