package chi.englishtest.com.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey @ColumnInfo(name = "_id") var id: Int,
    var question: String,
    @ColumnInfo(name = "test_id") var testId: Int,
    @Ignore var answers: List<Answer>
)