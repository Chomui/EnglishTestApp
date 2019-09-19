package chi.englishtest.com.core

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import chi.englishtest.com.data.db.AppDatabase
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.ApiManager
import chi.englishtest.com.network.RestApi
import chi.englishtest.com.data.sharedPref.SharedManager

class App : Application(), Injection {

    private val api: RestApi by lazy { ApiManager() }
    private val db: AppDatabase by lazy { Room.databaseBuilder(this, AppDatabase::class.java, "database").build() }

    override fun onCreate() {
        super.onCreate()

        /**SharedPreferences*/
        SharedManager.init(this)
    }

    override fun injectRestApi(): RestApi {
        return api
    }

    override fun injectDatabase(): AppDatabase {
        return db
    }


}