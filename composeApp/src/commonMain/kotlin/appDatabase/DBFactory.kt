package appDatabase

expect class DBFactory {
    fun createDatabase(): AppDatabase
}