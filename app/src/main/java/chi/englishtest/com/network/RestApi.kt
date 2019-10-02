package chi.englishtest.com.network

import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.model.net.results.ResultResponse
import chi.englishtest.com.model.net.test.TestResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @Multipart
    @POST("sessions")
    fun signIn(@Part("email") email: RequestBody, @Part("password") pass: RequestBody): Single<LogInResponse>

    @DELETE("sessions")
    fun signOut(@Query("token") pass: String = SharedManager.accessToken!!): Single<LogOutResponse>

    @GET("tests")
    fun getTests(): Single<List<TestResponse>>

    @GET("questions")
    fun getQuestionsByTestId(@Query("test_id") testId: Int): Single<List<QuestionResponse>>

    @Multipart
    @PUT("results")
    fun setAnswer(@Part("question_id") questionID: RequestBody,
                  @Part("answer_id") answerID: RequestBody)
            : Single<Response<ResultResponse>>

}