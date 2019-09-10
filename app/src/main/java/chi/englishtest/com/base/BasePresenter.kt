package chi.englishtest.com.base

interface BasePresenter<T: BaseView> {
    fun bindView(view: T)
    fun unbindView()
    fun onDestroy()
}