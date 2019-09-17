package chi.englishtest.com.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import chi.englishtest.com.R
import chi.englishtest.com.base.BaseActivity
import chi.englishtest.com.base.BasePresenter
import chi.englishtest.com.base.BasePresenterImpl
import chi.englishtest.com.base.BaseView
import chi.englishtest.com.data.db.EnglishContract
import chi.englishtest.com.network.Injection

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    override fun injectRepository(): MainPresenter = MainPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun buttonOnClickListener() {
    }
}
