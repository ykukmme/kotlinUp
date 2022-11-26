package fastcampus.aop.book.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fastcampus.aop.book.model.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE id == :id")
    fun getOneReview(id: Int): Review

    //있으면 새롭게 대체하도록 하는 방법
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReview(review: Review)
}