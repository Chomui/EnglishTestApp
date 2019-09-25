package chi.englishtest.com.data.fragment.question

import android.util.Log
import android.widget.Toast
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BasePresenterImpl
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionPresenterImpl(private val injection: Injection) : BasePresenterImpl<QuestionView>(injection), QuestionPresenter{

    override fun setAnswer(question: QuestionWithAnswers, answerId: Int) {
        viewRef?.get()?.startLoadingDialog()
        Log.e("Retrofit", "$question: $answerId")
        question.question?.userChoice = answerId
        db.questionDao()
            .updateQuestion(Question(question.question?.id, question.question?.question, question.question?.testId, answerId))
            .subscribeOn(Schedulers.io())
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.setNextQuestion()
            }
        // Some code to set answer to a server

    }

}