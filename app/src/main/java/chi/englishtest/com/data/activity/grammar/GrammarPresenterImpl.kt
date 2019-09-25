package chi.englishtest.com.data.activity.grammar

import android.util.Log
import androidx.room.EmptyResultSetException
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.db.entity.Test
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionProvider
import chi.englishtest.com.utils.answersToUiModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class GrammarPresenterImpl(private val injection: Injection) :
    BasePresenterImpl<GrammarView>(injection), GrammarPresenter {

    override fun getTest(id: Int) {
        var questions: List<QuestionResponse> = emptyList()
        viewRef?.get()?.startLoadingDialog()
        db.questionDao().getQuestionsWithAnswersByTestId(id)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.isEmpty()) {
                    // loadQuestionsNetworkAndCache(id, questions)
                    restApi.getQuestionsByTestId(id)
                        .toObservable()
                        .map {
                            questions = it
                            val list: MutableList<Question> = ArrayList()
                            for (question in it) {
                                list.add(
                                    Question(question.id!!, question.question!!, question.testId!!)
                                )
                            }
                            list
                        }
                        .flatMap {
                            db.questionDao().addAllQuestions(it).toObservable()
                        }
                        .flatMapIterable { questions }
                        .flatMap { db.answerDao().addAllAnswer(it.answersToUiModel()).toObservable() }
                        .flatMap { db.questionDao().getQuestionsWithAnswersByTestId(id).toObservable() }
                } else {
                    Observable.just(it)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                QuestionProvider.questions = it
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.openQuestionFragment()
            }, getDefaultErrorConsumer())
    }

//    private fun loadQuestionsNetworkAndCache(id: Int, q: List<QuestionResponse>) =
//
//
//    private fun loadAnswersNetworkAndCache(id: Int) =
//        restApi.getQuestionsByTestId(id)
//            .toObservable()
//            .flatMapIterable { it }
//            .flatMap {
//                db.answerDao().addAllAnswer(it.answersToUiModel())
//                    .toObservable()

    /*
    override fun testsIsEmpty() {
        viewRef?.get()?.startLoadingDialog()
        db.testDao()
            .getAllTests()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .flatMap {
                if(it.isEmpty()){
                    loadFromNetworkAndCache()
                }else{
                    Observable.just(it)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.openGrammar()
            }
    }

    private fun loadFromNetworkAndCache() =
        restApi.getTests()
            .toObservable()
            .flatMapIterable { it }
            .flatMap {
                db.testDao().addTest(Test(it.id!!, it.name!!, it.description!!)).toObservable()
            }
     */
}