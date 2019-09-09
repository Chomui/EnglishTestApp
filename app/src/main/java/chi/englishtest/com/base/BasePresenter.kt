package chi.englishtest.com.base

interface BasePresenter<T: BaseView> {
    fun updateUi()
}