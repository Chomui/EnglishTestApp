package chi.englishtest.com.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answer(
    @PrimaryKey @ColumnInfo(name = "_id") var id: Int,
    @ColumnInfo(name = "question_id") var questionId: Int,
    var answer: String
)