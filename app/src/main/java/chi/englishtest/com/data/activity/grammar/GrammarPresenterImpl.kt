package chi.englishtest.com.data.activity.grammar

import android.util.Log
import androidx.room.EmptyResultSetException
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.db.entity.Test
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionProvider
import chi.englishtest.com.utils.answersToUiModel
import chi.englishtest.com.utils.questionToEntityModels
import chi.englishtest.com.utils.toQuestionWithAnswers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class GrammarPresenterImpl(private val injection: Injection) :
    BasePresenterImpl<GrammarView>(injection), GrammarPresenter {

    override fun getTest(id: Int) {
        var questions: List<QuestionResponse> = emptyList()
        viewRef?.startLoadingDialog()
        compositeDisposable.add(
            db.questionDao().getQuestionsWithAnswersByTestId(id)
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    if(it.isNotEmpty()) {
                        QuestionProvider.questions = it
                    }
                }
                .filter { it.isEmpty() }
                .toObservable()
                .flatMap { restApi.getQuestionsByTestId(id) }
                .map {
                    questions = it
                    QuestionProvider.questions = it.toQuestionWithAnswers()
                    it.questionToEntityModels()
                }
                .flatMap {
                    db.questionDao().addAllQuestions(it).toObservable()
                }
                .flatMapIterable { questions }
                .flatMap {
                    db.answerDao().addAllAnswer(it.answersToUiModel()).toObservable()
                }
                .toList()
                .map {
                    val list: MutableList<QuestionWithAnswers> = ArrayList()
                    for (i in 0..100) {
                        list.addAll(QuestionProvider.questions)
                    }
                    QuestionProvider.questions = list
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    viewRef?.stopLoadingDialog()
                    viewRef?.openQuestionFragment()
                }, getDefaultErrorConsumer())
        )
    }
}