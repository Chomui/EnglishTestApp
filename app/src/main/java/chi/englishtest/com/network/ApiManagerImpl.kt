package chi.englishtest.com.network

import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.logout.LogOutResponse
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.model.net.results.ResultResponse
import chi.englishtest.com.model.net.test.TestResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response

class ApiManagerImpl(private val api: ApiManager) {

    fun signIn(email: String, pass: String): Single<LogInResponse> {
        val emailBody: RequestBody = RequestBody.create(MediaType.parse("text/plain"), email)
        val passBody: RequestBody = RequestBody.create(MediaType.parse("text/plain"), pass)
        return api.signIn(emailBody, passBody)
    }

    fun signOut(pass: String): Single<LogOutResponse> {
        return api.signOut(pass)
    }

    fun getTests(): Observable<List<TestResponse>> {
        return api.getTests()
    }

    fun getQuestionsByTestId(testId: Int): Observable<List<QuestionResponse>> {
        return api.getQuestionsByTestId(testId)
    }

    fun setAnswer(
        questionID: Int,
        answerID: Int
    ): Single<Response<ResultResponse>> {
        val questionID: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), questionID.toString())
        val answerID: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), answerID.toString())
        return api.setAnswer(questionID, answerID)
    }
}