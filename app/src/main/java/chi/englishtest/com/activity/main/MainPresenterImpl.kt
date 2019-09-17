package chi.englishtest.com.activity.main

import chi.englishtest.com.base.BasePresenterImpl
import chi.englishtest.com.network.Injection

class MainPresenterImpl(private var injection: Injection) : BasePresenterImpl<MainView>(injection), MainPresenter {

}