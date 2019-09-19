package chi.englishtest.com.data.activity.grammar

import android.os.Bundle
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.question.QuestionFragment
import chi.englishtest.com.network.Injection
import kotlinx.android.synthetic.main.activity_grammar.*

class GrammarActivity : BaseActivity<GrammarPresenter, GrammarView>(), GrammarView {

    companion object {
        var listQuestion: List<Question>? = null
    }

    override fun setQuestionToList(list: List<Question>) {
        listQuestion = list
    }

    override fun injectRepository(): GrammarPresenter = GrammarPresenterImpl(applicationContext as Injection)

    override fun provideLayout(): Int = R.layout.activity_grammar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentPlace, QuestionFragment())
            .commit()
    }

    override fun buttonOnClickListener() {
    }
}
