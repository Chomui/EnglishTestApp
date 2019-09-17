package chi.englishtest.com.network

import chi.englishtest.com.core.App
import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call

class ApiManager : RestApi {

    private var api: RestApi = NetManager.getRestApi()

    override fun signIn(email: RequestBody, pass: RequestBody): Single<LogInResponse> {
        return api.signIn(email, pass)
    }

    override fun signOut(pass: String): Single<LogOutResponse> {
        return api.signOut(pass)
    }

}