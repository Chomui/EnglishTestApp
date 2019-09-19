package chi.englishtest.com.data.activity.main

import android.os.Bundle
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.network.Injection

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    override fun injectRepository(): MainPresenter = MainPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_main

    override fun buttonOnClickListener() {
    }
}
