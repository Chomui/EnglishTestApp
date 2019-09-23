package chi.englishtest.com.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Question {
    @PrimaryKey @ColumnInfo(name = "_id") var id: Int? = null
    var question: String? = null
    @ColumnInfo(name = "test_id") var testId: Int? = null
    @Ignore var answers: List<Answer>? = null

    constructor(id: Int?, question: String?, testId: Int?) {
        this.id = id
        this.question = question
        this.testId = testId
    }

    @Ignore
    constructor(id: Int?, question: String?, testId: Int?, answers: List<Answer>?) {
        this.id = id
        this.question = question
        this.testId = testId
        this.answers = answers
    }
}