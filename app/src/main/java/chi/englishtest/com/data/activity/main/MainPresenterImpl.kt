package chi.englishtest.com.data.activity.main

import androidx.room.EmptyResultSetException
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.db.entity.Test
import chi.englishtest.com.model.net.test.TestResponse
import chi.englishtest.com.network.Injection
import io.reactivex.MaybeSource
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(private var injection: Injection) : BasePresenterImpl<MainView>(injection), MainPresenter {
    override fun getTests() {
        db.testDao().getAllTests()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if(it.isEmpty()) {
                    restApi.getTests().toObservable()
                        .flatMapIterable{ it }
                        .map {
                            db.testDao().addTest(Test(it.id!!, it.name!!, it.description!!))
                            for(question in it.questions!!) {
                                db.questionDao().addQuestion(Question(question.id!!, question.question!!, question.testId))
                                for(answer in question.answers!!) {
                                    db.answerDao().addAnswer(Answer(answer.id!!, answer.questionId!!, answer.answer!!))
                                }
                            }
                        }
                }
                viewRef?.get()?.openGrammar()
            }
            .subscribe({}, {
                handleDefaultNetError(it)
            })



    }

}