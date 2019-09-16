package chi.englishtest.com.activity.login

import chi.englishtest.com.R
import chi.englishtest.com.base.BaseActivity
import chi.englishtest.com.base.BasePresenter
import chi.englishtest.com.base.BasePresenterImpl
import chi.englishtest.com.base.BaseView
import chi.englishtest.com.network.Injection

class LoginActivity: BaseActivity<LoginPresenter, LoginView>(), BaseView {

    override fun injectRepository(): LoginPresenter = LoginPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_login

    override fun buttonOnClickListener() {}

}