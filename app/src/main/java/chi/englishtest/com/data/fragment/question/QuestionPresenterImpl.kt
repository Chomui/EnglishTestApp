package chi.englishtest.com.data.fragment.question

import chi.englishtest.com.data.fragment.BasePresenterImpl
import chi.englishtest.com.network.Injection

class QuestionPresenterImpl(private val injection: Injection) : BasePresenterImpl<QuestionView>(injection), QuestionPresenter{

    override fun getQuestion(num: Int) {

    }
}