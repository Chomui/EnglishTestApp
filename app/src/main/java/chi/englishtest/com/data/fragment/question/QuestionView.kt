package chi.englishtest.com.data.fragment.question

import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.fragment.BaseView

interface QuestionView : BaseView {
    fun setNextQuestion()
}