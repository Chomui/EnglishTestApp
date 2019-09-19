package chi.englishtest.com.model.net.test

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Answer(
    var id: Int? = null,
    @SerializedName("question_id") var questionId: Int? = null,
    var answer: String? = null
)