package chi.englishtest.com.data.db.entity

import androidx.room.*
import chi.englishtest.com.data.db.EnglishContract

@Entity
class Question(
    @PrimaryKey @ColumnInfo(name = "_id") var id: Int,
    var question: String,
    @ColumnInfo(name = EnglishContract.EnglishQuestion.TEST_ID) var testId: Int,
    @ColumnInfo(name = EnglishContract.EnglishQuestion.USER_CHOICE) var userChoice: Int = -1,
    @ColumnInfo(name = EnglishContract.EnglishQuestion.ON_SERVER) var onServer: Int = 0
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Question) return false

        if (id != other.id) return false
        if (question != other.question) return false
        if (testId != other.testId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + question.hashCode()
        result = 31 * result + testId
        return result
    }


}