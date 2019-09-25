package chi.englishtest.com.data.activity.grammar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.allquestions.AllQuestionsFragment
import chi.englishtest.com.data.fragment.question.QuestionFragment
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection
import kotlinx.android.synthetic.main.activity_grammar.*

class GrammarActivity : BaseActivity<GrammarPresenter, GrammarView>(), GrammarView, AllQuestionsFragment.OpenQuestionFragment  {

    override fun injectRepository(): GrammarPresenter = GrammarPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_grammar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbarGrammar)

        presenter.getTest(SharedManager.TEST_ID)
    }

    override fun openQuestionFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentPlace, QuestionFragment())
            .commit()
    }

    override fun fromAllToQuestionFragment() {
        openQuestionFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_grammar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentPlace, AllQuestionsFragment(this))
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun buttonOnClickListener() {}
}
