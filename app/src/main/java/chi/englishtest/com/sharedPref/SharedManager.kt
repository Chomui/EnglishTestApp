package chi.englishtest.com.sharedPref

import android.content.Context
import android.content.SharedPreferences

object SharedManager {
    private lateinit var sharedPref: SharedPreferences

    private const val PREFERENCES_NAME: String = "EnglishTestApp"

    fun init(context: Context) {
        sharedPref =  context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}