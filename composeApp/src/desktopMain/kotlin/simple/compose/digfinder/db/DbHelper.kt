package simple.compose.digfinder.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties

object DbHelper {

    val driver: SqlDriver by lazy { JdbcSqliteDriver("jdbc:sqlite:test.db", Properties(), AppDatabase.Schema) }

    val db: AppDatabase by lazy { AppDatabase(driver) }

    suspend fun getList() = withContext(Dispatchers.IO) {
        db.projectQueries.selectAll().executeAsList()
    }

    suspend fun add(
        name: String,
        path: String
    ) {
        db.projectQueries.insert(name, path, System.currentTimeMillis())
    }

    suspend fun delete(
        id: Long
    ) {
        db.projectQueries.delete(id)
    }
}