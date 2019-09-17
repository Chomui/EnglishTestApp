package chi.englishtest.com.activity.grammar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import chi.englishtest.com.R
import chi.englishtest.com.base.BaseActivity
import chi.englishtest.com.network.Injection

class GrammarActivity : BaseActivity<GrammarPresenter, GrammarView>(), GrammarView {
    override fun injectRepository(): GrammarPresenter = GrammarPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_grammar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)
    }

    override fun buttonOnClickListener() {
    }
}
