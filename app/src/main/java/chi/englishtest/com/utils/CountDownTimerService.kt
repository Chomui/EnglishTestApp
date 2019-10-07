package chi.englishtest.com.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.sharedPref.SharedManager
import java.util.concurrent.TimeUnit

class CountDownTimerService : Service() {

    private var timer: Counter = Counter(30000, 1000)
    private lateinit var pendingIntent: PendingIntent
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        pendingIntent =
            Intent(this, GrammarActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel("timer", "CountDownTimer", importance)
            notificationManager.createNotificationChannel(notificationChannel)
            NotificationCompat.Builder(applicationContext, notificationChannel.id)
        } else {
            NotificationCompat.Builder(applicationContext)
        }

        with(builder) {
            this.setSmallIcon(R.mipmap.ic_launcher)
            this.setContentTitle("Grammar Test")
            this.setContentText("Foreground")
            this.setContentIntent(pendingIntent)
        }

        timer.start()

        startForeground(1, builder.build())

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
        val timerInfoIntent = Intent(SharedManager.COUNT_DOWN_TIMER_INFO)
        timerInfoIntent.putExtra("VALUE", "Stopped")
        LocalBroadcastManager.getInstance(this@CountDownTimerService).sendBroadcast(timerInfoIntent)
    }

    inner class Counter(private val milliesInFuture: Long, private val countDownInterval: Long) :
        CountDownTimer(milliesInFuture, countDownInterval) {

        override fun onTick(p0: Long) {
            val millis: Long = p0
            val hms: String = String.format(
                "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                    TimeUnit.MILLISECONDS.toHours(
                        millis
                    )
                ),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        millis
                    )
                )
            );
            val timerInfoIntent = Intent(SharedManager.COUNT_DOWN_TIMER_INFO)
            timerInfoIntent.putExtra("VALUE", hms)
            LocalBroadcastManager.getInstance(this@CountDownTimerService)
                .sendBroadcast(timerInfoIntent)

            builder.setContentText(hms)
            notificationManager.notify(1, builder.build())
        }

        override fun onFinish() {
            val timerInfoIntent = Intent(SharedManager.COUNT_DOWN_TIMER_INFO)
            timerInfoIntent.putExtra("COMPLETED", "Completed")
            LocalBroadcastManager.getInstance(this@CountDownTimerService)
                .sendBroadcast(timerInfoIntent)
            builder.setContentText("Time is running out. Responses will be sent if you have Internet, or when he will appear")
            builder.setAutoCancel(true)
            notificationManager.notify(1, builder.build())
            stopSelf()
        }

    }
}

