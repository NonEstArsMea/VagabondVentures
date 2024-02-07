package raa.example.timerscreen.data.dataBase

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonParamEntity::class], version = 1, exportSchema = false)
abstract class PersonsDatabase : RoomDatabase() {

    abstract fun personParamDao(): Dao

    companion object {
        private var INSTANCE: PersonsDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "persons_profiles.db"

        fun getInstance(application: Application): PersonsDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    PersonsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}