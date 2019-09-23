package chi.englishtest.com.model.net.question

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Answer(
    var id: Int? = null,
    @SerializedName("question_id") var questionId: Int? = null,
    var answer: String? = null
)

