package fastcampus.aop.book

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fastcampus.aop.book.dao.HistoryDao
import fastcampus.aop.book.dao.ReviewDao
import fastcampus.aop.book.model.History
import fastcampus.aop.book.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}

fun getAppDatabase(context: Context): AppDatabase {

    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "BookSearchDB"
    ).build()
}