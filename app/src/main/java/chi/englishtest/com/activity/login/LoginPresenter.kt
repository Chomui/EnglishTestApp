package chi.englishtest.com.activity.login

import chi.englishtest.com.base.BasePresenter

interface LoginPresenter: BasePresenter<LoginView> {
    fun signIn(email: String, pass: String)
}