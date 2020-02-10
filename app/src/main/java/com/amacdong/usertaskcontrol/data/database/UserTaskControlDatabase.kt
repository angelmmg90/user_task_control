package com.amacdong.usertaskcontrol.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amacdong.data.model.TaskType
import com.amacdong.usertaskcontrol.data.database.daos.FarmDAO
import com.amacdong.usertaskcontrol.data.database.daos.TaskDAO
import com.amacdong.usertaskcontrol.data.database.daos.UserDAO
import com.amacdong.usertaskcontrol.data.database.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        TaskEntity::class,
        FarmEntity::class,
        WebsiteEntity::class,
        LocationPointEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UserTaskControlDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: UserTaskControlDatabase? = null

        private const val DATABASE_NAME = "utc-db"

        fun build(context: Context): UserTaskControlDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    UserTaskControlDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    var userDAO = database.userDAO()
                                    var taskDAO = database.taskDAO()
                                    populateDatabase(userDAO, taskDAO)
                                }
                            }
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                        }
                    })
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }

        suspend fun populateDatabase(userDAO: UserDAO, taskDAO: TaskDAO) {
            //USERS
            userDAO.deleteAll()
            var adminUser = UserEntity("1", "Angel", "admin", "password")
            userDAO.insertUser(adminUser)
            var technicalUser = UserEntity("2", "Pedro", "technical", "password", 6)
            userDAO.insertUser(technicalUser)
            technicalUser = UserEntity("3", "Daniel", "technical", "password", 3)
            userDAO.insertUser(technicalUser)
            technicalUser = UserEntity("4", "Gustavo", "technical", "password", 5)
            userDAO.insertUser(technicalUser)
            technicalUser = UserEntity("5", "Carlos", "technical", "password", 8)
            userDAO.insertUser(technicalUser)
            technicalUser = UserEntity("6", "David", "technical", "password", 4)
            userDAO.insertUser(technicalUser)

            //TASKS
            taskDAO.deleteAll()
            var newTask = TaskEntity(name = "Rellenar palet", description = "En la zona 5 hay espacio para más palets", duration = 1, type = TaskType.reponer.type, userId = "2", completed = false)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Entregar palets", description = "Llevar los palets con la traspaleta a la zona de venta", duration = 2, type = TaskType.cobrar.type, userId = "2", completed = true)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Empaquetar los palets", description = "Asegurar el palet para que no se caiga", duration = 1, type = TaskType.envolver.type, userId = "2", completed = false)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Inventario de productos", description = "Realizar un inventario de todos los productos", duration = 3, type = TaskType.etc.type, userId = "2", completed = false)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Cargar el transporte", description = "Llevar los palets a la zona de carga", duration = 1, type = TaskType.reponer.type, userId = "2", completed = false)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Recoger palets", description = "Recoger los palets caidos en la zona 5", duration = 2, type = TaskType.etc.type, userId = "2", completed = true)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Apilar aguas", description = "Apilar las cajas de agua de la zona 10", duration = 2, type = TaskType.envolver.type, userId = "2", completed = false)
            taskDAO.insertTask(newTask)

            newTask = TaskEntity(name = "Arreglar maquinaria", description = "La traspaleta de la zona 8 se encuentra volcada", duration = 1, type = TaskType.etc.type, userId = "2", completed = true)
            taskDAO.insertTask(newTask)
        }
    }

    abstract fun userDAO(): UserDAO
    abstract fun taskDAO(): TaskDAO
    abstract fun farmDAO(): FarmDAO

}