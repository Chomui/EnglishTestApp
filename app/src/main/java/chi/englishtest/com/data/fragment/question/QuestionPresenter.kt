package chi.englishtest.com.data.fragment.question

import chi.englishtest.com.data.fragment.BasePresenter

interface QuestionPresenter : BasePresenter<QuestionView> {
    fun getQuestion(num: Int)
}