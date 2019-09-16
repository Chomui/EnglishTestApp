package chi.englishtest.com.activity.login

import android.os.Bundle
import android.widget.Toast
import chi.englishtest.com.R
import chi.englishtest.com.base.BaseActivity
import chi.englishtest.com.base.BasePresenter
import chi.englishtest.com.base.BasePresenterImpl
import chi.englishtest.com.base.BaseView
import chi.englishtest.com.network.Injection
import chi.englishtest.com.sharedPref.SharedManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: BaseActivity<LoginPresenter, LoginView>(), LoginView {

    override fun injectRepository(): LoginPresenter = LoginPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_login


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun buttonOnClickListener() {
        buttonSignIn.setOnClickListener{ btnSignIn() }
    }

    override fun login() {
        Toast.makeText(this, SharedManager.accessToken, Toast.LENGTH_LONG).show()
    }

    private fun btnSignIn() {
        presenter.signIn(editTextEmail.text.toString(), editTextPass.text.toString())
    }
}