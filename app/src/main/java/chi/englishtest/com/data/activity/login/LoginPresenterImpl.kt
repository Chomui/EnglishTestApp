package chi.englishtest.com.data.activity.login

import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.network.Injection
import chi.englishtest.com.data.sharedPref.SharedManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

class LoginPresenterImpl(private var injection: Injection) : BasePresenterImpl<LoginView>(injection), LoginPresenter {

    override fun signIn(email: String, pass: String) {
        viewRef!!.get()!!.startLoadingDialog()
        val emailBody: RequestBody = RequestBody.create(MediaType.parse("text/plain"), email)
        val passBody: RequestBody = RequestBody.create(MediaType.parse("text/plain"), pass)
        restApi.signIn(emailBody, passBody)
            .subscribeOn(Schedulers.io())
            .map {
                SharedManager.isUserAuthorized = true
                SharedManager.accessToken = it.authenticationTokens?.first()?.token
                SharedManager.userEmail = email
                SharedManager.userPassword = pass
                SharedManager.userId = it.authenticationTokens?.first()?.userId
                SharedManager.isTeacher = it.isTeacher
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                viewRef?.get()?.stopLoadingDialog()
                viewRef?.get()?.login()
            }, getDefaultErrorConsumer())
    }
}