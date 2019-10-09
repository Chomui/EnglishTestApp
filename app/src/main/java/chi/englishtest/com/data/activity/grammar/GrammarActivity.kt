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
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import chi.englishtest.com.utils.QuestionProvider
import chi.englishtest.com.utils.ServiceManager


class GrammarActivity : BaseActivity<GrammarPresenter, GrammarView>(), GrammarView {

    override fun injectRepository(): GrammarPresenter =
        GrammarPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_grammar

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbarGrammar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (!ServiceManager.isMyServiceRunning(this, CountDownTimerService::class.java)) {
            Intent(this, CountDownTimerService::class.java).also {
                startService(it)
            }
        }

        //TODO: When back-end will have added a version variable - check if there is a new version
        presenter.getTest(SharedManager.TEST_ID)
    }

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1 != null && p1.action.equals(SharedManager.COUNT_DOWN_TIMER_INFO)) {
                if (p1.hasExtra(SharedManager.VALUE)) {
                    menu?.findItem(R.id.countdown_timer)?.title = p1.getStringExtra(SharedManager.VALUE)
                }
                if (p1.hasExtra(SharedManager.COMPLETED )) {
                    Toast.makeText(applicationContext, "Test completed", Toast.LENGTH_LONG).show()
                    pushNotification("Test completed. If there was no internet, answers will sent after connection is back")
                    checkTestIsDone()
                }
            }
        }
    }

    override fun onStart() {
        checkTestIsDone()
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(messageReceiver, IntentFilter(SharedManager.COUNT_DOWN_TIMER_INFO))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
    }

    override fun onBackPressed() {
        showDialogOnBackPressed(this)
    }

    override fun openQuestionFragment() {
        Log.i("Retrofit", "Change fragment, context ${javaClass}")
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
            .replace(R.id.fragmentPlace, QuestionFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_grammar, menu)
        if (menu != null) {
            this.menu = menu
        }
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

    private fun checkTestIsDone() {
        if (QuestionProvider.testIsDone) {
            QuestionProvider.testIsDone = false
            finish()
        }
    }

    private fun pushNotification(message: String) {
        val pendingIntent =
            Intent(this, GrammarActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel("timer", "CountDownTimer", importance)
            notificationManager.createNotificationChannel(notificationChannel)
            NotificationCompat.Builder(applicationContext, notificationChannel.id)
        } else {
            NotificationCompat.Builder(applicationContext)
        }

        with(builder) {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("Grammar Test")
            setContentText(message)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(1, builder.build())
    }

    private fun showDialogOnBackPressed(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        with(builder) {
            setMessage("You can use home button. If you want just get back - test will be canceled. Are you sure?")
            setTitle("Cancel test")
            setPositiveButton("Yes") { _, _ ->
                Intent(context, CountDownTimerService::class.java).also {
                    stopService(it)
                }
                pushNotification("Test is canceled")
                finish()
            }
            setNegativeButton("No") {_, _ -> }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
