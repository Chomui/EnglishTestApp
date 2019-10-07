package chi.englishtest.com.model.net.results

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultResponse(
    var id: String,
    @SerializedName("correct_answer") var correctAnswer: String,
    @SerializedName("question_id") var questionId: Int,
    @SerializedName("test_id") var testId: Int,
    @SerializedName("user_id") var userId: Int,
    var attempt: Int,
    @SerializedName("user_choice") var userChoice: Int
)
