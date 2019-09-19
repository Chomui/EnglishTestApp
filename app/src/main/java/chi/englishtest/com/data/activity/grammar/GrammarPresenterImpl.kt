package chi.englishtest.com.data.activity.grammar

import androidx.room.EmptyResultSetException
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.network.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class GrammarPresenterImpl(private val injection: Injection) : BasePresenterImpl<GrammarView>(injection), GrammarPresenter {
    override fun getTest(id: Int) {
        db.questionDao().getQuestionsByTestId(74)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                viewRef?.get()?.setQuestionToList(it)
            })
    }
}