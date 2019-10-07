package chi.englishtest.com.data.activity

interface BasePresenter<T : BaseView> {
    fun bindView(view: T)
    fun unbindView()
    fun onDestroy()
}