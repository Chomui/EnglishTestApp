package chi.englishtest.com.utils

import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question

object QuestionProvider {
    var currentIndexPosition: Int = 0
    var questions: List<QuestionWithAnswers> = ArrayList()
    @Volatile var  testIsDone: Boolean = false
}