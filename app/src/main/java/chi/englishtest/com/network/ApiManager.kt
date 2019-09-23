package chi.englishtest.com.network

import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.model.net.test.TestResponse
import io.reactivex.Single
import okhttp3.RequestBody

class ApiManager : RestApi {

    private var api: RestApi = NetManager.getRestApi()

    override fun signIn(email: RequestBody, pass: RequestBody): Single<LogInResponse> {
        return api.signIn(email, pass)
    }

    override fun signOut(pass: String): Single<LogOutResponse> {
        return api.signOut(pass)
    }

    override fun getTests(): Single<List<TestResponse>> {
        return api.getTests()
    }

    override fun getQuestionsByTestId(testId: Int): Single<List<QuestionResponse>> {
        return api.getQuestionsByTestId(testId)
    }

}