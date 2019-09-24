package chi.englishtest.com.data.fragment.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BaseFragment
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : BaseFragment<QuestionPresenter, QuestionView>(), QuestionView {

    companion object{
        var currentQuestionIndex: Int = 0
    }

    private var questions: List<Question>? = null
    private var answers: List<Answer>? = null

    override fun provideLayout(): Int = R.layout.fragment_question

    override fun injectRepository(): QuestionPresenter = QuestionPresenterImpl(activity?.applicationContext as Injection)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.getQuestions(SharedManager.TEST_ID)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioGroupQuestion.setOnCheckedChangeListener { p0, p1 ->
            when(p1) {
                R.id.radioButtonFirst -> {
                    radioGroupQuestion.clearCheck()
                    presenter.setAnswer(questions!![currentQuestionIndex].id!!, answers!!.first().id)
                }
                R.id.radioButtonSecond -> {
                    radioGroupQuestion.clearCheck()
                    presenter.setAnswer(questions!![currentQuestionIndex].id!!, answers!![1].id)
                }
                R.id.radioButtonThird -> {
                    radioGroupQuestion.clearCheck()
                    presenter.setAnswer(questions!![currentQuestionIndex].id!!, answers!![2].id)
                }
                R.id.radioButtonFourth -> {
                    radioGroupQuestion.clearCheck()
                    presenter.setAnswer(questions!![currentQuestionIndex].id!!, answers!![3].id)
                }
            }
        }
    }

    override fun setDataQuestions(questions: List<Question>) {
        this.questions = questions
        presenter.getAnswers(questions[currentQuestionIndex].id!!)
    }

    override fun showQuestion(answers: List<Answer>) {
        this.answers = answers
        textViewQuestion.text = questions!![currentQuestionIndex].question
        radioButtonFirst.text = answers.first().answer
        radioButtonSecond.text = answers[1].answer
        radioButtonThird.text = answers[2].answer
        radioButtonFourth.text = answers[3].answer
    }

    override fun setNextQuestion() {
        if (currentQuestionIndex < questions!!.size.minus(1)) {
            currentQuestionIndex++
            presenter.getAnswers(questions!![currentQuestionIndex].id!!)
        } else {
            Toast.makeText(view?.context, "Вопросы закончились", Toast.LENGTH_SHORT).show()
        }
    }

    override fun buttonOnClickListener() {

    }
}
