package chi.englishtest.com.data.fragment

import android.util.Log
import androidx.room.RoomDatabase
import chi.englishtest.com.data.db.AppDatabase
import chi.englishtest.com.model.Error
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.NetManager
import chi.englishtest.com.network.RestApi
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BasePresenterImpl<T: BaseView>(injection: Injection): BasePresenter<T> {
    var restApi: RestApi = injection.injectRestApi()
    var db: AppDatabase = injection.injectDatabase()
    var viewRef: WeakReference<T>? = null

    override fun bindView(view: T) {
        viewRef = WeakReference(view)
    }

    override fun unbindView() {
        viewRef = null
    }

    override fun onDestroy() {
        viewRef = null
    }

    protected fun getDefaultErrorConsumer(): Consumer<Throwable> {
        return Consumer {
            handleDefaultNetError(it)
        }
    }

    protected fun handleDefaultNetError(throwable: Throwable) {
        Log.e("Throwable", throwable.message)
        viewRef?.get()?.stopLoadingDialog()
        when (throwable) {
            is HttpException -> {
                throwable.code()
                try {
                    val jsonError = throwable.response()?.errorBody()?.string()
                    jsonError?.let {
                        val error = NetManager.getGson().fromJson(it, Error::class.java)
                        if (error.message.isNotEmpty()) {
                            viewRef?.get()?.showAlertDialog(error.message)
                        } else {
                            viewRef?.get()?.showAlertDialog(it)
                        }
                    }
                } catch (e: Exception) {
                    viewRef?.get()?.showAlertDialog("Unknown network error")
                    e.printStackTrace()
                }
            }
            is SocketTimeoutException -> {
                viewRef?.get()?.showAlertDialog("Socket error")
                throwable.printStackTrace()
            }
            is UnknownHostException -> {
                viewRef?.get()?.showAlertDialog("No internet")
                throwable.printStackTrace()
            }
            else -> {
                viewRef?.get()?.showAlertDialog("Unknown Error")
                throwable.printStackTrace()
            }
        }
    }
}