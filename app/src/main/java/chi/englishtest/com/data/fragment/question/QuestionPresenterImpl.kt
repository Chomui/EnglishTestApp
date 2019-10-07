package chi.englishtest.com.data.fragment.question

import android.content.Context
import android.util.Log
import androidx.work.*
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BasePresenterImpl
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionSetAnswerWorker
import chi.englishtest.com.utils.ServiceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import java.util.concurrent.TimeUnit

class QuestionPresenterImpl(private val injection: Injection) :
    BasePresenterImpl<QuestionView>(injection), QuestionPresenter {

    override fun setAnswer(context: Context, question: QuestionWithAnswers, answerId: Int) {
        question.question.userChoice = answerId
        if (ServiceManager.isNetworkAvailable(context)) {
            Log.i("Retrofit", "Network available, sending answer")
            db.questionDao()
                .updateQuestion(
                    Question(
                        question.question?.id,
                        question.question?.question,
                        question.question?.testId,
                        answerId,
                        0
                    )
                )
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    viewRef?.get()?.setNextQuestion()
                }
                .toObservable()
                .flatMap { restApi.setAnswer(question.question.id, answerId).toObservable() }
                .filter { !it.isSuccessful }
                .doOnNext{
                    Log.e("Retrofit", "Network Available, Answer is not sent, code: ${it.code()}")
                }
                .flatMap {
                    db.questionDao().updateQuestion(
                            Question(
                                question.question.id,
                                question.question.question,
                                question.question.testId,
                                answerId,
                                1
                            )
                        )
                        .toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {}, getDefaultErrorConsumer())
        } else {
            Log.e("Retrofit", "Network isn't available, set Worker")
            setQuestionWithNoSentStatus(question, answerId, 1)
            setAnswerWhenInternetAppear(context)
        }
    }


    private fun setQuestionWithNoSentStatus(
        question: QuestionWithAnswers,
        answerId: Int,
        noSent: Int
    ) {
        db.questionDao().updateQuestion(
            Question(
                question.question.id,
                question.question.question,
                question.question.testId,
                answerId,
                1
            )
        )
            .subscribeOn(Schedulers.io())
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }


    private fun setAnswerWhenInternetAppear(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val setQuestionWorkRequest = OneTimeWorkRequestBuilder<QuestionSetAnswerWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork("setAnswers", ExistingWorkPolicy.KEEP, setQuestionWorkRequest)
    }

}