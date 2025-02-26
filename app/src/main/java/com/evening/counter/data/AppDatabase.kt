package com.evening.counter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evening.counter.data.dao.TableItemDao
import com.evening.counter.data.entity.TableItem

@Database(
    entities = [TableItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tableItemDao(): TableItemDao
}