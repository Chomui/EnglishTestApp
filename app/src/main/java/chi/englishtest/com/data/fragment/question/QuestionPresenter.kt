package chi.englishtest.com.data.fragment.question

import android.content.Context
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BasePresenter

interface QuestionPresenter : BasePresenter<QuestionView> {
    fun setAnswer(context: Context , question: QuestionWithAnswers , answerId: Int)
}