package chi.englishtest.com.data.activity.splash

import androidx.room.EmptyResultSetException
import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.network.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SplashPresenterImpl(private val injection: Injection) : BasePresenterImpl<SplashView>(injection), SplashPresenter {

}