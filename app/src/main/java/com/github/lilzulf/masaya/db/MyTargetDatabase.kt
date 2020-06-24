package com.github.lilzulf.masaya.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.dao.MyTargetDao

@Database(entities = arrayOf(MyTargetModel::class), version = 1,
    exportSchema = false)
public abstract class MyTargetDatabase : RoomDatabase() {
    abstract fun myTargetDao(): MyTargetDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
// same time.
        @Volatile
        private var INSTANCE: MyTargetDatabase? = null
        fun getDatabase(context: Context): MyTargetDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyTargetDatabase::class.java,
                    "my_target_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}