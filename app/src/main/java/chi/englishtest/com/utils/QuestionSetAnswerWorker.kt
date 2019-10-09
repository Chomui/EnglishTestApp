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
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.RequestBody
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList

class QuestionSetAnswerWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val restApi = (applicationContext as Injection).injectRestApi()
    private val db = (applicationContext as Injection).injectDatabase()

    override fun doWork(): Result {
        Log.i("Retrofit", "In worker")
        lateinit var questionTemp: Question
        val results = ArrayList<Result>()
        db.questionDao()
            .getQuestionsUnsent()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { question ->
                questionTemp = question
                restApi.setAnswer(question.id, question.userChoice!!).toObservable()
            }
            .doOnError {
                Log.e("Retrofit", "Worker exception: ${it.message}")
                it.printStackTrace()
                results.add(Result.failure())
            }
            .doOnNext {
                if (!it.isSuccessful) {
                    results.add(handleNetCode(it.code()))
                } else {
                    results.add(Result.success())
                }
            }
            .filter { it.isSuccessful }
            .flatMap {
                Log.i("Retrofit", "Worker: Answer sent successfully to the server")
                updateDbForSussessfullySendQuestion(questionTemp)
            }
            .toList()
            .blockingGet()

        if (results.contains(Result.retry())) {
            return Result.retry()
        } else if(results.contains(Result.failure())) {
            return Result.failure()
        }

        return Result.success()
    }

    private fun updateDbForSussessfullySendQuestion(questionTemp: Question): Observable<Int>? {
        return db.questionDao().updateQuestion(
            Question(
                questionTemp.id,
                questionTemp.question,
                questionTemp.testId,
                questionTemp.userChoice,
                2
            )
        )
            .toObservable()
    }

    private fun handleNetCode(code: Int): Result {
        return when (code) {
            408,
            429,
            in 501..599 -> {
                Log.e("Retrofit", "Worker: retry, code $code")
                Result.retry()
            }
            else -> {
                Log.e("Retrofit", "Worker: failure, code $code")
                Result.failure()
            }
        }
    }
}