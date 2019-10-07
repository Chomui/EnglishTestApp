package chi.englishtest.com.model.net.test

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Answer(
    var id: Int,
    @SerializedName("question_id") var questionId: Int,
    var answer: String
)