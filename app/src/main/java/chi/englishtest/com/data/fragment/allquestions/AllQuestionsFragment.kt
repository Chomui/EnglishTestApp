package chi.englishtest.com.data.fragment.allquestions


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import chi.englishtest.com.R
import chi.englishtest.com.data.fragment.BaseFragment
import chi.englishtest.com.network.Injection


class AllQuestionsFragment : BaseFragment<AllQuestionsPresenter, AllQuestionsView>(), AllQuestionsView {
    override fun provideLayout(): Int = R.layout.fragment_all_questions

    override fun injectRepository(): AllQuestionsPresenter = AllQuestionsPresenterImpl(activity?.applicationContext as Injection)

    override fun buttonOnClickListener() {
    }
}
