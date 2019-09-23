package chi.englishtest.com.core

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import chi.englishtest.com.data.db.AppDatabase
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.ApiManager
import chi.englishtest.com.network.RestApi
import chi.englishtest.com.data.sharedPref.SharedManager
import com.facebook.stetho.Stetho

class App : Application(), Injection {

    private var api: RestApi? = null
    private var db: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()

        api = ApiManager()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()

        /**SharedPreferences*/
        SharedManager.init(this)

        Stetho.initializeWithDefaults(this)
    }

    override fun injectRestApi(): RestApi {
        return api!!
    }

    override fun injectDatabase(): AppDatabase {
        return db!!
    }


}