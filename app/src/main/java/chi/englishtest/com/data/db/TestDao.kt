package chi.englishtest.com.data.db

import android.provider.BaseColumns
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import chi.englishtest.com.data.db.entity.Test
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TestDao {

    @Query("SELECT * FROM " + EnglishContract.EnglishTest.TABLE_NAME)
    fun getAllTests(): Single<List<Test>>

    @Query("SELECT * FROM " + EnglishContract.EnglishTest.TABLE_NAME +
                " WHERE " + BaseColumns._ID + " = :id")
    fun getTestById(id: Int): Single<Test>

    @Insert
    fun addTest(test: Test): Single<Long>

    @Insert
    fun addAllTests(vararg tests: Test): Completable

    @Insert
    fun addAllTests(tests: List<Test>): Single<List<Long>>

    @Delete
    fun removeTest(test: Test): Completable

    @Delete
    fun removeAllTests(test: List<Test>): Completable
}