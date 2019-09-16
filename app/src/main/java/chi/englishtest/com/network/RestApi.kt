package chi.englishtest.com.network

import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import chi.englishtest.com.sharedPref.SharedManager
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RestApi {

    @POST("sessions")
    fun signIn(@Query("email") email: String,
               @Query("password") pass: String): Single<LogInResponse>

    @DELETE("sessions")
    fun signOut(@Query("token") pass: String = SharedManager.accessToken!!): Single<LogOutResponse>
}