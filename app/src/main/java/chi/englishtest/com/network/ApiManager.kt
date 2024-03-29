package chi.englishtest.com.network

import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.model.net.results.ResultResponse
import chi.englishtest.com.model.net.test.TestResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response

class ApiManager : RestApi {

    private var api: RestApi = NetManager.getRestApi()

    override fun signIn(
        email: RequestBody,
        pass: RequestBody
    ): Single<LogInResponse> {
        return api.signIn(email, pass)
    }

    override fun signOut(pass: String): Single<LogOutResponse> {
        return api.signOut(pass)
    }

    override fun getTests(): Observable<List<TestResponse>> {
        return api.getTests()
    }

    override fun getQuestionsByTestId(testId: Int): Observable<List<QuestionResponse>> {
        return api.getQuestionsByTestId(testId)
    }

    override fun setAnswer(
        questionID: RequestBody,
        answerID: RequestBody
    ): Single<Response<ResultResponse>> {
        return api.setAnswer(questionID, answerID)
    }

}