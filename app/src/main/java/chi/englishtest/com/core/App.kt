package chi.englishtest.com.core

import android.app.Application
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.ApiManager
import chi.englishtest.com.network.RestApi
import chi.englishtest.com.sharedPref.SharedManager

class App : Application(), Injection {

    companion object {
        lateinit var instance: App
            private set
    }

    private lateinit var api: RestApi

    override fun onCreate() {
        super.onCreate()

        instance = this

        /**Retrofit*/
        api = ApiManager()

        /**SharedPreferences*/
        SharedManager.init(this)
    }

    override fun injectRepository(): RestApi {
        return api
    }




}