package chi.englishtest.com.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import chi.englishtest.com.data.db.AppDatabase
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.RestApi
import okhttp3.MediaType
import okhttp3.RequestBody

class QuestionSetAnswerWorker(appContext: Context, workerParams: WorkerParameters, injection: Injection)
    : Worker(appContext, workerParams) {

    private val restApi: RestApi = injection.injectRestApi()
    private val db: AppDatabase = injection.injectDatabase()

    override fun doWork(): Result {
        var result: Result = Result.success()
        val answersResult = db.questionDao()
            .getQuestionsUnsent()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { question ->
                val questionID: RequestBody = RequestBody.create(MediaType.parse("text/plain"), question.id.toString())
                val answerID: RequestBody = RequestBody.create(MediaType.parse("text/plain"), question.userChoice.toString())
                restApi.setAnswer(questionID, answerID)
                    .doOnSuccess {
                        result = if(it.code() != null) {
                            when(it.code()) {
                                408,
                                429,
                                in 501..599 -> Result.retry()
                                else -> Result.failure()
                            }
                        } else {
                            Result.retry()
                        }
                    }
                    .toObservable()
                    .flatMap {
                        db.questionDao().updateQuestion(Question(question.id,question.question, question.testId, question.userChoice, 0))
                            .toObservable()
                    }
            }
            .doOnError {
                it.printStackTrace()
                result = Result.failure()
            }
            .toList()
            .blockingGet()

        return result
    }
}