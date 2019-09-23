package chi.englishtest.com.data.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    override fun openGrammar() {
        Toast.makeText(this, SharedManager.accessToken, Toast.LENGTH_LONG).show()
        //startActivity(Intent(this, GrammarActivity::class.java))
    }

    override fun injectRepository(): MainPresenter =
        MainPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.testsIsEmpty()
    }

    override fun buttonOnClickListener() {
    }
}
