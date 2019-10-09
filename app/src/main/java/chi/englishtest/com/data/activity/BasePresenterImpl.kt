package chi.englishtest.com.data.activity

import android.util.Log
import androidx.room.RoomDatabase
import chi.englishtest.com.data.db.AppDatabase
import chi.englishtest.com.model.Error
import chi.englishtest.com.network.Injection
import chi.englishtest.com.network.NetManager
import chi.englishtest.com.network.RestApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BasePresenterImpl<T : BaseView>(injection: Injection) :
    BasePresenter<T> {
    var restApi = injection.injectRestApi()
    var db = injection.injectDatabase()
    var viewRef: T? = null
    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }


    override fun bindView(view: T) {
        viewRef = view
    }

    override fun unbindView() {
        viewRef = null
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun getDefaultErrorConsumer(): Consumer<Throwable> {
        return Consumer {
            handleDefaultNetError(it)
        }
    }

    protected fun handleDefaultNetError(throwable: Throwable) {
        Log.e("Throwable", throwable.message)
        viewRef?.stopLoadingDialog()
        when (throwable) {
            is HttpException -> {
                throwable.code()
                try {
                    val jsonError = throwable.response()?.errorBody()?.string()
                    jsonError?.let {
                        val error = NetManager.getGson().fromJson(it, Error::class.java)
                        if (error.message.isNotEmpty()) {
                            viewRef?.showAlertDialog(error.message)
                        } else {
                            viewRef?.showAlertDialog(it)
                        }
                    }
                } catch (e: Exception) {
                    viewRef?.showAlertDialog("Unknown network error")
                    e.printStackTrace()
                }
            }
            is SocketTimeoutException -> {
                viewRef?.showAlertDialog("Socket error")
                throwable.printStackTrace()
            }
            is UnknownHostException -> {
                viewRef?.showAlertDialog("No internet")
                throwable.printStackTrace()
            }
            else -> {
                viewRef?.showAlertDialog("Unknown Error")
                throwable.printStackTrace()
            }
        }
    }
}