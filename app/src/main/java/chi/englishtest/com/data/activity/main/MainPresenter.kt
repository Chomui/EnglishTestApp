package chi.englishtest.com.data.activity.main

import chi.englishtest.com.data.activity.BasePresenter

interface MainPresenter : BasePresenter<MainView> {
    fun testsIsEmpty()
}