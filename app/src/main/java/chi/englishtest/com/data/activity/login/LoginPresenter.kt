package chi.englishtest.com.data.activity.login

import chi.englishtest.com.data.activity.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun signIn(email: String, pass: String)
}