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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class QuestionPresenterImpl(private val injection: Injection) :
    BasePresenterImpl<QuestionView>(injection), QuestionPresenter {

    override fun setAnswer(context: Context, question: QuestionWithAnswers, answerId: Int) {
        question.question.userChoice = answerId
        if (ServiceManager.isNetworkAvailable(context)) {
            Log.i("Retrofit", "Network available, sending answer")
            compositeDisposable.add(
                db.questionDao()
                    .updateQuestion(
                        Question(
                            question.question.id,
                            question.question.question,
                            question.question.testId,
                            question.question.userChoice
                        )
                    )
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .flatMap { restApi.setAnswer(question.question.id, answerId).toObservable() }
                    .doOnNext {
                        if (!it.isSuccessful) {
                            Log.e(
                                "Retrofit",
                                "Network Available, Answer is not sent, code: ${it.code()}"
                            )
                        }
                    }
                    .filter { it.isSuccessful }
                    .flatMap {
                        db.questionDao().updateQuestion(
                            Question(
                                question.question.id,
                                question.question.question,
                                question.question.testId,
                                question.question.userChoice,
                                1
                            )
                        )
                            .toObservable()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        Consumer {
                        }, getDefaultErrorConsumer()
                    )
            )
        } else {
            Log.e("Retrofit", "Network isn't available, set Worker")
            setQuestionWithSentStatus(question, answerId)
            setAnswerWhenInternetAppear(context)
        }
        viewRef?.setNextQuestion()
    }


    private fun setQuestionWithSentStatus(
        question: QuestionWithAnswers,
        answerId: Int,
        onServer: Int = 0
    ) {
        compositeDisposable.add(
            db.questionDao().updateQuestion(
                Question(
                    question.question.id,
                    question.question.question,
                    question.question.testId,
                    answerId,
                    onServer
                )
            )
                .subscribeOn(Schedulers.io())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

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