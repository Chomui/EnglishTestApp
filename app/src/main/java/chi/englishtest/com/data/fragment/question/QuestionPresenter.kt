package chi.englishtest.com.data.fragment.question

import chi.englishtest.com.data.fragment.BasePresenter

interface QuestionPresenter : BasePresenter<QuestionView> {
    fun getQuestions(testId: Int)
    fun getAnswers(questionId: Int)
    fun setAnswer(questionId: Int , answerId: Int)
}