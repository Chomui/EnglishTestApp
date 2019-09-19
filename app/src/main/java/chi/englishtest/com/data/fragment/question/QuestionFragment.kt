package chi.englishtest.com.data.fragment.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.fragment.BaseFragment
import chi.englishtest.com.network.Injection
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : BaseFragment<QuestionPresenter, QuestionView>(), QuestionView {

    companion object{
        var currentQuestionIndex: Int = 0
    }

    private val radioButtonFirst: RadioButton? by lazy { view?.findViewById<RadioButton>(R.id.radioButtonFirst) }

    override fun provideLayout(): Int = R.layout.fragment_question

    override fun injectRepository(): QuestionPresenter = QuestionPresenterImpl(activity?.applicationContext as Injection)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val question = GrammarActivity.listQuestion!![currentQuestionIndex]
        textViewQuestion.text = question.question
        radioButtonFirst?.text = question.answers!!.first().answer
        radioButtonSecond?.text = question.answers!![1].answer
        radioButtonThird?.text = question.answers!![2].answer
        radioButtonFourth?.text = question.answers!![3].answer
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
