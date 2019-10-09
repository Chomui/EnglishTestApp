package chi.englishtest.com.data.activity.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.activity.BasePresenter
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.data.activity.BaseView
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.activity.login.LoginActivity
import chi.englishtest.com.data.activity.main.MainActivity
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.CountDownTimerService
import chi.englishtest.com.utils.ServiceManager
import android.Manifest.permission
import android.Manifest.permission.READ_SMS
import android.Manifest.permission.READ_EXTERNAL_STORAGE

class SplashActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (SharedManager.accessToken == SharedManager.DEFAULT) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            if (ServiceManager.isMyServiceRunning(this, CountDownTimerService::class.java)) {
                startActivity(Intent(this, GrammarActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        finish()
    }
}
