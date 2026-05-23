import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import appDatabase.DBFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP-RoomDB-Example",
    ) {
        App(DBFactory())
    }
}