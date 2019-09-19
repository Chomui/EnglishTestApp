package chi.englishtest.com.data.fragment

interface BasePresenter<T: BaseView> {
    fun bindView(view: T)
    fun unbindView()
    fun onDestroy()
}