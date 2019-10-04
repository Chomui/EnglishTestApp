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
        question.question?.userChoice = answerId
        if (ServiceManager.isNetworkAvailable(context)) {
            val questionID: RequestBody = RequestBody.create(MediaType.parse("text/plain"), question.question?.id.toString())
            val answerID: RequestBody = RequestBody.create(MediaType.parse("text/plain"), question.question?.userChoice.toString())

            Log.i("Retrofit", "Network available, sending answer")
            restApi.setAnswer(questionID, answerID)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap {
                    if (it.isSuccessful) {
                        Log.i("Retrofit", "Network Avaivable, Answer is sent, code: ${it.code()}")
                        Observable.just(setQuestionWithNoSentStatus(question, answerId, 2))
                    } else {
                        Log.e("Retrofit", "Network Avaivable, Answer is not sent, code: ${it.code()}")
                        Observable.just(setQuestionWithNoSentStatus(question, answerId, 1))
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                }, getDefaultErrorConsumer())
        } else {
            Log.e("Retrofit", "Network isn't available, set Worker")
            setQuestionWithNoSentStatusBlockingGet(question, answerId, 1)
            setAnswerWhenInternetAppear(context)
        }
        viewRef?.get()?.setNextQuestion()
    }

    private fun setQuestionWithNoSentStatusBlockingGet(question: QuestionWithAnswers, answerId: Int, noSent: Int): Int {
        return db.questionDao()
            .updateQuestion(
                Question(
                    question.question?.id,
                    question.question?.question,
                    question.question?.testId,
                    answerId,
                    noSent
                )
            )
            .subscribeOn(Schedulers.io())
            .toObservable()
            .blockingSingle()
    }

    private fun setQuestionWithNoSentStatus(question: QuestionWithAnswers, answerId: Int, noSent: Int): Observable<Int> {
        return db.questionDao()
            .updateQuestion(
                Question(
                    question.question?.id,
                    question.question?.question,
                    question.question?.testId,
                    answerId,
                    noSent
                )
            )
            .subscribeOn(Schedulers.io())
            .toObservable()
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