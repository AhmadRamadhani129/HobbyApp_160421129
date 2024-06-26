package com.example.hobbyapp_160421129.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hobbyapp_160421129.util.DB_NAME
import com.example.hobbyapp_160421129.util.MIGRATION_1_2

@Database(entities = arrayOf(News::class, Users::class), version = 2)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun hobbyDao():HobbyDao

    companion object {
        @Volatile private var instance:NewsDatabase ?=null
        private val LOCK = Any()

        fun buildDatabase(context:Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                DB_NAME
            )
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    operator fun invoke(context: Context) {
        if (instance != null) {
            synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}