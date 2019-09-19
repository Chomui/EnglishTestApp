package chi.englishtest.com.model.net.question

import com.google.gson.annotations.SerializedName

data class Answers(
    var id: String? = null,
    @SerializedName("question_id") var questionId: String? = null,
    var answer: String? = null
)