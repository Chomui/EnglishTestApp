package chi.englishtest.com.core

import android.app.Application
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.ApiManager
import chi.englishtest.com.network.RestApi
import chi.englishtest.com.sharedPref.SharedManager

class App : Application(), Injection {

    private lateinit var api: RestApi

    override fun onCreate() {
        super.onCreate()

        /**Retrofit*/
        api = ApiManager()

        /**SharedPreferences*/
        SharedManager.init(this)
    }

    override fun injectRepository(): RestApi {
        return api
    }
}