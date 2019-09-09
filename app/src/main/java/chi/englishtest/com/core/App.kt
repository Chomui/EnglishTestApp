package chi.englishtest.com.core

import android.app.Application
import chi.englishtest.com.base.Injection
import chi.englishtest.com.network.ApiManager
import chi.englishtest.com.network.RestApi

class App : Application(), Injection {

    private lateinit var api: RestApi

    override fun onCreate() {
        super.onCreate()

        /*Retrofit*/
        api = ApiManager()
    }

    override fun injectRepository(): RestApi {
        return api
    }
}