package chi.englishtest.com.network

import chi.englishtest.com.BuildConfig
import chi.englishtest.com.data.sharedPref.SharedManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetManager {
    private var connectTimeout = 3L
    private var writeTimeout = 3L
    private var readTimeout = 3L
    private var gson: Gson

    init {
        gson = createGson()
    }

    fun getRestApi(): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(connectTimeout, TimeUnit.MINUTES)
        .writeTimeout(writeTimeout, TimeUnit.MINUTES)
        .readTimeout(readTimeout, TimeUnit.MINUTES)
        .addInterceptor(ResponseInterceptor())
        .build()

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()


    class ResponseInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request()
                .newBuilder()
                .addHeader("Authorization", SharedManager.accessToken!!)
                .build())
        }
    }

    private fun createGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setLenient()
            .create()
    }

    fun getGson(): Gson {
        return gson
    }
}