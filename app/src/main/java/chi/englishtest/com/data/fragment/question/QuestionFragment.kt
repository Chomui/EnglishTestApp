package chi.englishtest.com.data.fragment.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.grammar.GrammarActivity
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BaseFragment
import chi.englishtest.com.data.fragment.allquestions.AllQuestionsFragment
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.network.Injection
import chi.englishtest.com.utils.QuestionProvider

class QuestionFragment : BaseFragment<QuestionPresenter, QuestionView>(), QuestionView {

    override fun provideLayout(): Int = R.layout.fragment_question

    override fun injectRepository(): QuestionPresenter =
        QuestionPresenterImpl(activity?.applicationContext as Injection)

    private var isDataSetting: Boolean = false

    private lateinit var radioGroupQuestion: RadioGroup
    private lateinit var textViewQuestion: TextView
    private lateinit var radioButtonFirst: RadioButton
    private lateinit var radioButtonSecond: RadioButton
    private lateinit var radioButtonThird: RadioButton
    private lateinit var radioButtonFourth: RadioButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setQuestionToUi()
    }

    override fun setNextQuestion() {
        if (QuestionProvider.currentIndexPosition < QuestionProvider.questions.size.minus(1)) {
            QuestionProvider.currentIndexPosition++
            (activity as GrammarActivity).openQuestionFragment()
        } else {
            Toast.makeText(view?.context, "Вопросы закончились", Toast.LENGTH_SHORT).show()
        }
    }

    override fun buttonOnClickListener() {
        radioGroupQuestion.setOnCheckedChangeListener(onCheckedChangeListener())
    }

    private fun onCheckedChangeListener() =
        RadioGroup.OnCheckedChangeListener { p0, p1 ->
            if (!isDataSetting) {
                val currentQuestion =
                    QuestionProvider.questions[QuestionProvider.currentIndexPosition]
                when (p1) {
                    R.id.radioButtonFirst -> {
                        presenter.setAnswer(
                            activity?.applicationContext!!,
                            currentQuestion,
                            currentQuestion.answers.first().id
                        )
                    }
                    R.id.radioButtonSecond -> {
                        presenter.setAnswer(
                            activity?.applicationContext!!,
                            currentQuestion,
                            currentQuestion.answers[1].id
                        )
                    }
                    R.id.radioButtonThird -> {
                        presenter.setAnswer(
                            activity?.applicationContext!!,
                            currentQuestion,
                            currentQuestion.answers[2].id
                        )
                    }
                    R.id.radioButtonFourth -> {
                        presenter.setAnswer(
                            activity?.applicationContext!!,
                            currentQuestion,
                            currentQuestion.answers[3].id
                        )
                    }
                }
            }
        }

    private fun setQuestionToUi() {

        val currentQuestion = QuestionProvider.questions[QuestionProvider.currentIndexPosition]
        val answers = currentQuestion.answers
        textViewQuestion.text = currentQuestion.question.question
        radioButtonFirst.text = answers.first().answer
        radioButtonSecond.text = answers[1].answer
        radioButtonThird.text = answers[2].answer
        radioButtonFourth.text = answers[3].answer

        val userChoice = currentQuestion.question.userChoice
        isDataSetting = true
        when (userChoice) {
            answers.first().id -> radioButtonFirst.isChecked = true

            answers[1].id -> radioButtonSecond.isChecked = true

            answers[2].id -> radioButtonThird.isChecked = true

            answers[3].id -> radioButtonFourth.isChecked = true
        }
        isDataSetting = false
    }
}
