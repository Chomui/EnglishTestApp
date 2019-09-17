package chi.englishtest.com.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Test(
    @PrimaryKey @ColumnInfo(name = "_id") var id: Int,
    var name: String,
    var description: String
)