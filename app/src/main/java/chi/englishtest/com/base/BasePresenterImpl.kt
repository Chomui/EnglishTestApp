package chi.englishtest.com.base

import chi.englishtest.com.network.RestApi

abstract class BasePresenterImpl<T: BaseView>(injection: Injection) : BasePresenter<T> {
    private val restApi: RestApi = injection.injectRepository()
}