package chi.englishtest.com.data.db.entity

import androidx.room.*
import chi.englishtest.com.data.db.EnglishContract

@Entity
class Question {
    @PrimaryKey @ColumnInfo(name = "_id") var id: Int? = null
    var question: String? = null
    @ColumnInfo(name = EnglishContract.EnglishQuestion.TEST_ID) var testId: Int? = null
    @ColumnInfo(name = EnglishContract.EnglishQuestion.USER_CHOICE) var userChoice: Int? = null
    @ColumnInfo(name = EnglishContract.EnglishQuestion.NOT_SENT) var notSent: Int? = null

    constructor(id: Int?, question: String?, testId: Int?) {
        this.id = id
        this.question = question
        this.testId = testId
    }

    @Ignore
    constructor(id: Int?, question: String?, testId: Int?, userChoice: Int?, notSent: Int?) {
        this.id = id
        this.question = question
        this.testId = testId
        this.userChoice = userChoice
        this.notSent = notSent
    }

    @Ignore
    constructor(id: Int?, question: String?, testId: Int?, userChoice: Int?) {
        this.id = id
        this.question = question
        this.testId = testId
        this.userChoice = userChoice
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Question) return false

        if (id != other.id) return false
        if (question != other.question) return false
        if (testId != other.testId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (question?.hashCode() ?: 0)
        result = 31 * result + (testId ?: 0)
        return result
    }


}