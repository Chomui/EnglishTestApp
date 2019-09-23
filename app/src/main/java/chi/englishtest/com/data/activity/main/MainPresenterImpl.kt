package chi.englishtest.com.data.activity.main

import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.data.db.entity.Test
import chi.englishtest.com.network.Injection
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(private var injection: Injection) : BasePresenterImpl<MainView>(injection),
    MainPresenter {

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
            .subscribe( Consumer {
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.openGrammar()
            }, getDefaultErrorConsumer())
    }

    private fun loadFromNetworkAndCache() =
        restApi.getTests()
            .toObservable()
            .map {
                val list: MutableList<Test> = ArrayList<Test>()
                for(test in it) {
                    list.add(Test(test.id!!, test.name!!, test.description!!))
                }
                list
            }
            .flatMap {
                db.testDao().addAllTests(it).toObservable()
            }

    /*override fun loadTestsFromNetworkAndCache() {
        restApi.getTests()
            .toObservable()
            .flatMapIterable { it }
            .doOnNext {
                Observable.fromCallable {
                    Log.e("Retrofit", "${it.id}, ${it.name}, ${it.description!!}")
                    db.testDao().addTest(Test(it.id!!, it.name!!, it.description!!))
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        viewRef?.get()?.dbIsEmpty(false)
                    }
            }
    }*/
        /*
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            viewRef?.get()?.stopLoadingDialog()
            viewRef?.get()?.openGrammar()
        }*/

        /* for(i in 0..4) {
             db.testDao().addTest(Test(i, "name", "descriprion"))
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe()
         }
         viewRef?.get()?.stopLoadingDialog()
         viewRef?.get()?.openGrammar()*/



    //.flatMap{ Observable.just(db.testDao().addTest(Test(it.id!!, it.name!!, it.description!!))) }
    /**save into databse */
    //.flatMap { db.testDao().getAllTests().toObservable() }
}

