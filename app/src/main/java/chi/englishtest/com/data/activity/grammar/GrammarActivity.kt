package chi.englishtest.com.data.activity.grammar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.fragment.allquestions.AllQuestionsFragment
import chi.englishtest.com.data.fragment.question.QuestionFragment
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.CountDownTimerService
import kotlinx.android.synthetic.main.activity_grammar.*
import android.app.ActivityManager
import chi.englishtest.com.utils.ServiceManager


class GrammarActivity : BaseActivity<GrammarPresenter, GrammarView>(), GrammarView {

    override fun injectRepository(): GrammarPresenter = GrammarPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_grammar

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!ServiceManager.isMyServiceRunning(this, CountDownTimerService::class.java)) {
            Intent(this, CountDownTimerService::class.java).also {
                startService(it)
            }
        }

        setSupportActionBar(toolbarGrammar)

        presenter.getTest(SharedManager.TEST_ID)
    }

    private val messageReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if(p1 != null && p1.action.equals(SharedManager.COUNT_DOWN_TIMER_INFO)) {
                if(p1.hasExtra("VALUE")) {
                    menu?.findItem(R.id.countdown_timer)?.title = p1.getStringExtra("VALUE")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, IntentFilter(SharedManager.COUNT_DOWN_TIMER_INFO))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
    }

    override fun openQuestionFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
            .replace(R.id.fragmentPlace, QuestionFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_grammar, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                    .replace(R.id.fragmentPlace, AllQuestionsFragment())
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun buttonOnClickListener() {}


}
