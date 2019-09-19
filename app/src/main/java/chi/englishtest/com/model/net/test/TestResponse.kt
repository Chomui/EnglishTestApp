package chi.englishtest.com.model.net.test

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TestResponse(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var questions: List<Question>? = null,
    @SerializedName("questions_count") var questionsCount: Int? = null
)