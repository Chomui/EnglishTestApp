package chi.englishtest.com.data.fragment.question

import android.content.Context
import android.util.Log
import android.widget.Toast
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BasePresenterImpl
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionProvider
import chi.englishtest.com.utils.ServiceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionPresenterImpl(private val injection: Injection) :
    BasePresenterImpl<QuestionView>(injection), QuestionPresenter {

    override fun setAnswer(context: Context, question: QuestionWithAnswers, answerId: Int) {
        //viewRef?.get()?.startLoadingDialog()
        Log.e("Retrofit", "$question: $answerId")
        question.question?.userChoice = answerId
        if (ServiceManager.isNetworkAvailable(context)) {
            //Todo: sent question to server
            db.questionDao()
                .updateQuestion(
                    Question(
                        question.question?.id,
                        question.question?.question,
                        question.question?.testId,
                        answerId
                    )
                )
                .subscribeOn(Schedulers.io())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
        } else {
            db.questionDao()
                .updateQuestion(
                    Question(
                        question.question?.id,
                        question.question?.question,
                        question.question?.testId,
                        answerId,
                        1
                    )
                )
                .subscribeOn(Schedulers.io())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
        }

        viewRef?.get()?.setNextQuestion()

    }

}