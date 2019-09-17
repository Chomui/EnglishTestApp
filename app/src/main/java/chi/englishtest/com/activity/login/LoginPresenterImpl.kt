package chi.englishtest.com.activity.login

import android.util.Log
import chi.englishtest.com.base.BasePresenterImpl
import chi.englishtest.com.core.App
import chi.englishtest.com.model.Error
import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.NetManager
import chi.englishtest.com.network.RestApi
import chi.englishtest.com.sharedPref.SharedManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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