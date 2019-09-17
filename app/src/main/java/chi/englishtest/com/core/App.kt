package chi.englishtest.com.core

import android.app.Application
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.ApiManager
import chi.englishtest.com.network.RestApi
import chi.englishtest.com.data.sharedPref.SharedManager

class App : Application(), Injection {

    private val api: RestApi by lazy { ApiManager() }

    override fun onCreate() {
        super.onCreate()

        /**SharedPreferences*/
        SharedManager.init(this)
    }

    override fun injectRepository(): RestApi {
        return api
    }




}