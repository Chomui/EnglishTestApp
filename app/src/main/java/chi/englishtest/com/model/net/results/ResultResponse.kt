package chi.englishtest.com.model.net.results

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultResponse(
    var id: String? = null,
    @SerializedName("correct_answer") var correctAnswer: String? = null,
    @SerializedName("question_id") var questionId: Int? = null,
    @SerializedName("test_id") var testId: Int? = null,
    @SerializedName("user_id") var userId: Int? = null,
    var attempt: Int? = null,
    @SerializedName("user_choice") var userChoice: Int? = null
)
