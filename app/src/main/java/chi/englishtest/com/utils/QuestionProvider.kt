package chi.englishtest.com.utils

import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question

class QuestionProvider {
    companion object {
        var currentIndexPosition: Int = 0
        var questions: List<QuestionWithAnswers> = ArrayList()
    }
}