package chi.englishtest.com.utils

import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Test
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.model.net.test.TestResponse

fun QuestionResponse.answersToUiModel(): List<Answer> {
    val list: MutableList<Answer> = ArrayList<Answer>()
    val answers = this.answers
    for(i in 0 until this.answers!!.size) {
        list.add(Answer(answers!![i].id!!, answers[i].questionId!!, answers[i].answer!!))
    }
    return list
}
