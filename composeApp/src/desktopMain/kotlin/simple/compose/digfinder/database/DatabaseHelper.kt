package simple.compose.digfinder.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Properties

object DatabaseHelper {

   val driver: SqlDriver by lazy {
      val dbPath = Paths.get(
         System.getProperty("user.home"),
         "Library", "Application Support", "DuplicateImageFinder", "dig_finder.db"
      )
      Files.createDirectories(dbPath.parent)

      val driver = JdbcSqliteDriver("jdbc:sqlite:${dbPath.toAbsolutePath()}")
      // 如果是首次运行，需要创建 schema
      if (!Files.exists(dbPath)) {
         AppDatabase.Schema.create(driver)
      }
      driver
//      JdbcSqliteDriver("jdbc:sqlite:dig_finder.db", Properties(), AppDatabase.Schema)
   }

   val db: AppDatabase by lazy { AppDatabase(driver) }

   suspend fun getProjectList() = withContext(Dispatchers.IO) {
      db.projectQueries.selectAll().executeAsList()
   }

   suspend fun addProject(
      name: String,
      path: String
   ) {
      db.projectQueries.insert(name, path, System.currentTimeMillis()).await()
   }

   suspend fun deleteProject(
      id: Long
   ) {
      db.projectQueries.delete(id).await()
   }

   suspend fun getProject(id: Long) = withContext(Dispatchers.IO) {
      db.projectQueries.get(id).executeAsOne()
   }

   suspend fun getDirPathList(id: Long) = withContext(Dispatchers.IO) {
      db.projectDirsQueries.getList(id).executeAsList()
   }

   suspend fun addDirPath(
      projectId: Long,
      path: String
   ) = withContext(Dispatchers.IO) {
      val id = db.projectDirsQueries.insert(projectId, path, System.currentTimeMillis()).await()
      db.projectDirsQueries.get(id).executeAsOne()
   }
}