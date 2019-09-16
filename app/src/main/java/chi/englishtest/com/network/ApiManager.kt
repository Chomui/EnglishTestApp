package chi.englishtest.com.network

import chi.englishtest.com.core.App
import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import io.reactivex.Single
import retrofit2.Call

class ApiManager : RestApi {

    val restApi: RestApi = App.instance.injectRepository()

    override fun signIn(email: String, pass: String): Single<LogInResponse> {
        return restApi.signIn(email, pass)
    }

    override fun signOut(pass: String): Single<LogOutResponse> {
        return restApi.signOut(pass)
    }

}