package chi.englishtest.com.data.db

import androidx.room.*
import chi.englishtest.com.data.db.entity.Question
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuestionDao {

    @Query("SELECT * FROM " + EnglishContract.EnglishQuestion.TABLE_NAME +
            " WHERE " + EnglishContract.EnglishQuestion.TEST_ID + " = :testId")
    fun getQuestionsByTestId(testId: Int): Single<List<Question>>

    @Query("SELECT * FROM " + EnglishContract.EnglishQuestion.TABLE_NAME +
            " WHERE " + EnglishContract.EnglishQuestion.TEST_ID + " = :testId")
    fun getQuestionsWithAnswersByTestId(testId: Int): Single<List<QuestionWithAnswers>>

    @Query("SELECT * FROM " + EnglishContract.EnglishQuestion.TABLE_NAME +
            " WHERE " + EnglishContract.EnglishQuestion.NOT_SENT + " = 1")
    fun getQuestionsUnsent(): Single<List<Question>>

    @Insert
    fun addQuestion(question: Question): Single<Long>

    @Insert
    fun addAllQuestions(vararg questions: Question): Single<List<Long>>

    @Insert
    fun addAllQuestions(questions: List<Question>): Single<List<Long>>

    @Delete
    fun removeQuestion(question: Question): Completable

    @Delete
    fun removeAllQuestions(questions: List<Question>)

    @Update
    fun updateQuestion(question: Question): Single<Int>
}