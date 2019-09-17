package chi.englishtest.com.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import chi.englishtest.com.data.db.entity.Question
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuestionDao {

    @Query("SELECT * FROM " + EnglishContract.EnglishQuestion.TABLE_NAME +
        " WHERE " + EnglishContract.EnglishQuestion.TEST_ID + " = :testId")
    fun getQuestionsByTestId(testId: Int): Single<List<Question>>

    @Insert
    fun addQuestion(question: Question): Completable

    @Insert
    fun addAllQuestions(vararg questions: Question): Completable

    @Insert
    fun addAllQuestions(questions: List<Question>): Completable

    @Delete
    fun removeQuestion(question: Question): Completable

    @Delete
    fun removeAllQuestions(questions: List<Question>)
}