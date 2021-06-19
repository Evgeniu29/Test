package com.genius.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class], version = 3, exportSchema = true)
abstract class RoomAppDb: RoomDatabase() {


    abstract fun userDao(): UserDao?

    companion object {
        private var INSTANCE: RoomAppDb? = null






        fun getAppDatabase(context: Context): RoomAppDb? {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                        context.applicationContext, RoomAppDb::class.java, "AppDBB"
                ).fallbackToDestructiveMigration().build()

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
    }
