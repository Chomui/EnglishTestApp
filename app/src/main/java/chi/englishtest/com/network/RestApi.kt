package chi.englishtest.com.network

import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import chi.englishtest.com.sharedPref.SharedManager
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RestApi {

    @Multipart
    @POST("sessions")
    fun signIn(@Part("email") email: RequestBody, @Part("password") pass: RequestBody): Single<LogInResponse>

    @DELETE("sessions")
    fun signOut(@Query("token") pass: String = SharedManager.accessToken!!): Single<LogOutResponse>
}