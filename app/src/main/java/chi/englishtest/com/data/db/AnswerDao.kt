package chi.englishtest.com.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AnswerDao {

    @Query("SELECT * FROM " + EnglishContract.EnglishAnswer.TABLE_NAME +
            " WHERE " + EnglishContract.EnglishAnswer.QUESTION_ID + " = :questionId")
    fun getAnswersByQuestionId(questionId: Int): Single<List<Answer>>

    @Insert
    fun addAnswer(answer: Answer): Single<Long>

    @Insert
    fun addAllAnswer(vararg answers: Answer): Single<List<Long>>

    @Insert
    fun addAllAnswer(answers: List<Answer>): Single<List<Long>>

    @Delete
    fun removeAnswer(answer: Answer): Completable

    @Delete
    fun removeAllAnswers(answers: List<Answer>): Completable
}