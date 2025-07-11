package simple.compose.digfinder.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.AppDatabase
import java.util.Properties

object DbHelper {

    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db", Properties(), AppDatabase.Schema)

}