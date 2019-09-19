package chi.englishtest.com.data.fragment.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import chi.englishtest.com.R
import chi.englishtest.com.data.fragment.BaseFragment
import chi.englishtest.com.network.Injection
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : BaseFragment<QuestionPresenter, QuestionView>(), QuestionView {

    private val radioButtonFirst: RadioButton? by lazy { view?.findViewById<RadioButton>(R.id.radioButtonFirst) }

    override fun provideLayout(): Int = R.layout.fragment_question

    override fun injectRepository(): QuestionPresenter = QuestionPresenterImpl(activity?.applicationContext as Injection)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun buttonOnClickListener() {
       /* radioGroupQuestion.setOnCheckedChangeListener { p0, p1 ->
            val radioButton = p0.findViewById(p0.checkedRadioButtonId) as? RadioButton
            radioButton?.let {
                if(radioButton.isChecked) {

                }
            }
        }*/

    }

    override fun showQuestion() {
    }




}
