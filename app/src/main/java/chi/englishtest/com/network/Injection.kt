package chi.englishtest.com.network

import chi.englishtest.com.data.db.AppDatabase

interface Injection {
    fun injectRestApi(): RestApi
    fun injectDatabase(): AppDatabase
}