import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmp_with_roomdb.composeapp.generated.resources.Res
import kmp_with_roomdb.composeapp.generated.resources.github_logo

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        val listRepository = ListRepository()
        val viewModel = ListViewModel(listRepository)

        val items = viewModel.users.collectAsState()

        NavHost(navController, startDestination = "list") {

            composable("list") {
                ListScreen(state = items) { user ->
                    navController.navigate("detail/${user.id}")
                }
            }

            composable("detail/{id}") {
                val id = it.arguments?.getString("id") ?: ""
                val user = items.value.data?.first { it.id == id.toInt() }
                DetailScreen(user!!) {
                    navController.navigateUp()
                }
            }

        }

    }
}

@Composable
fun ListScreen(
    state: State<ListUiState>,
    onItemClick: (GithubRepo) -> Unit
) {

    if (state.value.isLoading == true) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp).align(Alignment.Center))
        }
    } else if (state.value.data != null) {
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Text(
                    "Github Top Kotlin Repos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            items(state.value.data!!, key = { item -> item.id }) { item ->
                Card(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(item)
                        },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(Res.drawable.github_logo),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(16.dp)
                                .size(50.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                        Column {
                            Text(text = item.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "${item.stars} stars", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(item: GithubRepo, goBack: () -> Unit) {

    //User Detail
    Scaffold(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets(0.dp)),
                title = {
                    Text(
                        item.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier.padding(it).fillMaxSize().padding(16.dp)
        ) {
            val painter = painterResource(Res.drawable.github_logo)
            Box(
                modifier = Modifier
                    .fillMaxWidth().height(300.dp)
            ) {
                Image(
                    painter,
                    modifier = Modifier.fillMaxWidth().height(300.dp).padding(8.dp),
                    contentDescription = item.name
                )
            }

            Text(
                item.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Stars: ", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(item.stars, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class GithubRepo(val id: Int, val name: String, val stars: String, val description: String)