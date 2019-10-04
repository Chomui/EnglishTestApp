package chi.englishtest.com.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import chi.englishtest.com.data.db.AppDatabase
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.model.Error
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.NetManager
import chi.englishtest.com.network.RestApi
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class QuestionSetAnswerWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private val restApi: RestApi = (applicationContext as Injection).injectRestApi()
    private val db: AppDatabase = (applicationContext as Injection).injectDatabase()

    override fun doWork(): Result {
        Log.i("Retrofit", "In worker")
        val results = db.questionDao()
            .getQuestionsUnsent()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { question ->
                val questionID: RequestBody = RequestBody.create(MediaType.parse("text/plain"), question.id.toString())
                val answerID: RequestBody = RequestBody.create(MediaType.parse("text/plain"), question.userChoice.toString())
                restApi.setAnswer(questionID, answerID)
                    .toObservable()
                    .flatMap {
                        if (it.isSuccessful) {
                            Log.i("Retrofit", "Worker: Answer sent successfully to the server")
                            db.questionDao().
                                updateQuestion(
                                    Question(
                                        question.id,question.question,
                                        question.testId,
                                        question.userChoice,
                                        2
                                    )
                                )
                                .toObservable()
                                .map { Result.success() }
                        } else {
                            Log.i("Retrofit", "Worker: Answer not sent to the server")
                            if (it.code() != null) {
                                when (it.code()) {
                                    408,
                                    429,
                                    in 501..599 -> {
                                        Log.e("Retrofit", "Worker: retry, code ${it.code()}")
                                        Observable.just(Result.retry())
                                    }
                                    else -> {
                                        Log.e("Retrofit", "Worker: failure, code ${it.code()}")
                                        Observable.just(Result.failure())
                                    }
                                }
                            } else {
                                Log.e("Retrofit", "Worker: failure, without code")
                                Observable.just(Result.failure())
                            }
                        }
                    }
            }
            .doOnError {
                Log.e("Retrofit", "Worker error")
                it.printStackTrace()
            }
            .toList()
            .blockingGet()

        if (results.isNotEmpty()) {
            for (result in results) {
                if (result != Result.success()) {
                    return result
                }
            }
        } else {
            Log.i("Retrofit", "results is empty")
        }

        return Result.success()
    }
}