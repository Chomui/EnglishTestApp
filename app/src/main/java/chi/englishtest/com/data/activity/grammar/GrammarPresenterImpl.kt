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
            }, getDefaultErrorConsumer())
    }

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