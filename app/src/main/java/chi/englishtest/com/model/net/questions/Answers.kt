package chi.englishtest.com.model.net.questions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Answers(
    var id: String? = null,
    @SerializedName("question_id") var questionId: String? = null,
    var answer: String? = null
)



