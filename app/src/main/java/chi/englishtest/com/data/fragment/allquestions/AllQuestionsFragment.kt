package chi.englishtest.com.data.fragment.allquestions


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import chi.englishtest.com.R
import chi.englishtest.com.adapter.QuestionAdapter
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BaseFragment
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionProvider
import kotlinx.android.synthetic.main.fragment_all_questions.*

class AllQuestionsFragment(private val callback: OpenQuestionFragment)
    : BaseFragment<AllQuestionsPresenter, AllQuestionsView>(), AllQuestionsView, QuestionAdapter.OnQuestionClickListener {

    interface OpenQuestionFragment {
        fun fromAllToQuestionFragment()
    }

    override fun provideLayout(): Int = R.layout.fragment_all_questions

    override fun injectRepository(): AllQuestionsPresenter = AllQuestionsPresenterImpl(activity?.applicationContext as Injection)

    private val adapter = QuestionAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAllQuestions.layoutManager = LinearLayoutManager(view.context)
        recyclerViewAllQuestions.adapter = adapter

        adapter.submitList(QuestionProvider.questions)
    }

    override fun onQuestionClick(id: Int) {
        QuestionProvider.currentIndexPosition = id
        callback.fromAllToQuestionFragment()
    }

    override fun buttonOnClickListener() {
    }
}
