package chi.englishtest.com.data.activity.login

import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.cacheUserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class LoginPresenterImpl(private var injection: Injection) :
    BasePresenterImpl<LoginView>(injection), LoginPresenter {

    override fun signIn(email: String, pass: String) {
        viewRef?.startLoadingDialog()
        compositeDisposable.add(
            restApi.signIn(email, pass)
            .subscribeOn(Schedulers.io())
            .map { it.cacheUserData(email, pass) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                viewRef?.stopLoadingDialog()
                viewRef?.login()
            }, getDefaultErrorConsumer()))
    }
}