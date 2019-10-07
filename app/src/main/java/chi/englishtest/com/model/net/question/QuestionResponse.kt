package chi.englishtest.com.model.net.question

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    var id: Int,
    var question: String,
    @SerializedName("test_id")
    var testId: Int,
    var number: Int,
    var answers: List<Answer>
)
