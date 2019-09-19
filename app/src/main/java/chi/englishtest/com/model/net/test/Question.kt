package chi.englishtest.com.model.net.test

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Question(
    var id: Int? = null,
    var question: String? = null,
    @SerializedName("test_id") var testId: Int? = null,
    var number: Int? = null,
    var answers: List<Answer>? = null
)
