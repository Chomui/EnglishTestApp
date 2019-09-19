package chi.englishtest.com.data.activity.main

import chi.englishtest.com.data.activity.BasePresenterImpl
import chi.englishtest.com.network.Injection

class MainPresenterImpl(private var injection: Injection) : BasePresenterImpl<MainView>(injection), MainPresenter {

}