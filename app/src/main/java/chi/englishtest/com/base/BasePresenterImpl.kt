package chi.englishtest.com.base

import chi.englishtest.com.network.RestApi
import java.lang.ref.WeakReference

abstract class BasePresenterImpl<T: BaseView>(injection: Injection) : BasePresenter<T> {
    private var restApi: RestApi = injection.injectRepository()
    private var viewRef: WeakReference<T>? = null

    override fun bindView(view: T) {
        viewRef = WeakReference(view)
    }

    override fun unbindView() {
        viewRef = null
    }

    override fun onDestroy() {
        viewRef = null
    }

}