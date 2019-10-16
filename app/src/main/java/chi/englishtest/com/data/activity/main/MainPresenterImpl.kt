package chi.englishtest.com.data.activity.main

import android.content.Context
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(private var injection: Injection) : BasePresenterImpl<MainView>(injection),
    MainPresenter {

    override fun testsIsEmpty() {
        viewRef?.startLoadingDialog()
        compositeDisposable.add(
            db.testDao()
                .getAllTests()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .doOnNext {
                    if (it.isNotEmpty()) {
                        viewRef?.stopLoadingDialog()
                    }
                }
                .filter { it.isEmpty() }
                .flatMap { restApi.getTests() }
                .map { it.testsToEntityModels() }
                .flatMap { db.testDao().addAllTests(it).toObservable() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    viewRef?.stopLoadingDialog()
                }, getDefaultErrorConsumer())
        )
    }

    override fun updateDbForGrammarTest(context: Context, testID: Int) {
        viewRef?.startLoadingDialog()
        if (ServiceManager.isMyServiceRunning(context, CountDownTimerService::class.java)) {
            viewRef?.stopLoadingDialog()
            viewRef?.openGrammar()
        } else {
            compositeDisposable.add(
                db.questionDao()
                    .updateUserChoiceByTestId(testID, -1)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        viewRef?.stopLoadingDialog()
                        viewRef?.openGrammar()
                    })
        }
    }
}

