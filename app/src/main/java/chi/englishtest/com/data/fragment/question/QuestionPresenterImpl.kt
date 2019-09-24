package chi.englishtest.com.data.fragment.question

import android.util.Log
import android.widget.Toast
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BasePresenterImpl
import chi.englishtest.com.network.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionPresenterImpl(private val injection: Injection) : BasePresenterImpl<QuestionView>(injection), QuestionPresenter{

    /*override fun getQuestions(testId: Int) {
        viewRef?.get()?.startLoadingDialog()
        db.questionDao()
            .getQuestionsByTestId(testId)
            .subscribeOn(Schedulers.io())
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.setDataQuestions(it)
            }
    }*/

    override fun getAnswers(questionId: Int) {
        viewRef?.get()?.startLoadingDialog()
        db.answerDao()
            .getAnswersByQuestionId(questionId)
            .subscribeOn(Schedulers.io())
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.showQuestion(it)
            }
    }

    override fun setAnswer(question: Question, answerId: Int) {
        Log.e("Retrofit", "$question: $answerId")
        question.userChoice = answerId
        val dbQuestion = Question(question.id, question.question, question.testId, answerId)
        db.questionDao()
            .updateQuestion(dbQuestion)
            .subscribeOn(Schedulers.io())
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
        // Some code to set answer to a server
        viewRef?.get()?.setNextQuestion()
    }

}