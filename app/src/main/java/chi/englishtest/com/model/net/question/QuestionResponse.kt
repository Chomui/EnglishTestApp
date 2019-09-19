package chi.englishtest.com.model.net.question

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    var id: Int? = null,
    var question: String? = null,
    @SerializedName("test_id") var testId: Int? = null,
    var number: Int? = null,
    var answers: Answers? = null
)

