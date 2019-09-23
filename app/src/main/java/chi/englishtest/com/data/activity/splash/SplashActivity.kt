package chi.englishtest.com.data.activity.splash

import android.content.Intent
import android.os.Bundle
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.activity.login.LoginActivity
import chi.englishtest.com.data.activity.main.MainActivity
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection

class SplashActivity : BaseActivity<SplashPresenter, SplashView>(), SplashView {
    override fun provideLayout(): Int = R.layout.activity_splash

    override fun injectRepository(): SplashPresenter = SplashPresenterImpl(applicationContext as Injection)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(SharedManager.accessToken == SharedManager.DEFAULT) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun buttonOnClickListener() {}
}