package chi.englishtest.com.data.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.CountDownTimerService
import chi.englishtest.com.utils.QuestionProvider
import chi.englishtest.com.utils.ServiceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    override fun injectRepository(): MainPresenter =
        MainPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter.testsIsEmpty()
    }

    override fun buttonOnClickListener() {
        buttonGrammar.setOnClickListener { prepareGrammarTest() }
    }

    override fun openGrammar() {
        Toast.makeText(this, SharedManager.accessToken, Toast.LENGTH_LONG).show()
        startActivity(Intent(this, GrammarActivity::class.java))
    }

    private fun prepareGrammarTest() {
        if (!ServiceManager.isMyServiceRunning(this, CountDownTimerService::class.java)) {
            QuestionProvider.testIsDone = false
        }
        presenter.updateDbForGrammarTest(this, SharedManager.TEST_ID)
    }
}
