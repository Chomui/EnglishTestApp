package chi.englishtest.com.base

import chi.englishtest.com.network.RestApi

interface Injection {
    fun injectRepository(): RestApi
}