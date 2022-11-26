package fastcampus.aop.book

import androidx.room.Database
import androidx.room.RoomDatabase
import fastcampus.aop.book.dao.HistoryDao
import fastcampus.aop.book.dao.ReviewDao
import fastcampus.aop.book.model.History
import fastcampus.aop.book.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}