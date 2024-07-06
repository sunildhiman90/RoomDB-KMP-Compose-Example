import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListRepository() {

    suspend fun getKotlinTopRepos(): Flow<List<GithubRepo>> {
        return flow {
            emit(getRepos())
        }
    }

    private fun getRepos(): List<GithubRepo> {
        val items = listOf(
            GithubRepo(1, "JetBrains/kotlin", "48.3k", "The Kotlin Programming Language."),
            GithubRepo(2, "square/okhttp", "45.5k", "Square’s meticulous HTTP client for the JVM, Android, and GraalVM"),
            GithubRepo(3, "android/architecture-samples", "44.1k", "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps."),
            GithubRepo(4, "bannedbook/fanqiang", "37.6k", "Over the firewall - Scientific Internet access"),
            GithubRepo(5, "shadowsocks/shadowsocks-android", "34.8k", "A shadowsocks client for Android."),
        )
        return items
    }




}