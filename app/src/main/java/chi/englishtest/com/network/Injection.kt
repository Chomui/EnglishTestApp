package chi.englishtest.com.network

import chi.englishtest.com.network.RestApi

interface Injection {
    fun injectRepository(): RestApi
}