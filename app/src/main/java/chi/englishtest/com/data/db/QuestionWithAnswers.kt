package chi.englishtest.com.data.db

import androidx.room.Embedded
import androidx.room.Relation
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question

class QuestionWithAnswers {
    @Embedded var question: Question? = null
    @Relation(parentColumn = "_id", entityColumn = "question_id") var answers: List<Answer>? = null

    constructor(question: Question, answers: List<Answer>) {
        this.question = question
        this.answers = answers
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QuestionWithAnswers) return false

        if (question != other.question) return false

        return true
    }

    override fun hashCode(): Int {
        return question?.hashCode() ?: 0
    }


}