package chi.englishtest.com.data.sharedPref

import android.content.Context
import android.content.SharedPreferences

object SharedManager {
    private var sharedPref: SharedPreferences? = null

    private const val PREFERENCES_NAME: String = "EnglishTestApp"
    private const val ACCESS_TOKEN: String = "ACCESS_TOKEN"
    private const val USER_EMAIL: String = "USER_EMAIL"
    private const val USER_NAME: String = "USER_NAME"
    private const val IS_USER_AUTHORIZED: String = "IS_USER_AUTHORIZED"
    private const val USER_PASSWORD: String = "USER_PASSWORD"
    private const val USER_ID: String = "USER_ID"
    private const val IS_TEACHER: String = "IS_TEACHER"
    const val DEFAULT: String = "DEFAULT"
    const val TEST_ID: Int = 98

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences(
            PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    var isUserAuthorized: Boolean?
        get() = sharedPref!!.getBoolean(IS_USER_AUTHORIZED, false)
        set(value) = sharedPref!!.edit()
            .putBoolean(IS_USER_AUTHORIZED, value!!)
            .apply()

    var accessToken: String?
        get() = sharedPref!!.getString(
            ACCESS_TOKEN,
            DEFAULT
        )
        set(value) = sharedPref!!.edit()
            .putString(ACCESS_TOKEN, value)
            .apply()

    var userEmail: String?
        get() = sharedPref!!.getString(
            USER_EMAIL,
            DEFAULT
        )
        set(value) = sharedPref!!.edit()
            .putString(USER_EMAIL, value)
            .apply()

    var userName: String?
        get() = sharedPref!!.getString(
            USER_NAME,
            DEFAULT
        )
        set(value) = sharedPref!!.edit()
            .putString(USER_NAME, value)
            .apply()

    var userPassword: String?
        get() = sharedPref!!.getString(
            USER_PASSWORD,
            DEFAULT
        )
        set(value) = sharedPref!!.edit()
            .putString(USER_PASSWORD, value)
            .apply()

    var userId: Int?
        get() = sharedPref!!.getInt(USER_ID, -1)
        set(value) = sharedPref!!.edit()
            .putInt(USER_ID, value!!)
            .apply()

    var isTeacher: Boolean?
        get() = sharedPref!!.getBoolean(IS_TEACHER, false)
        set(value) = sharedPref!!.edit()
            .putBoolean(IS_TEACHER, value!!)
            .apply()
}