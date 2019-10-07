package chi.englishtest.com.model.net.test

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TestResponse(
    var id: Int,
    var name: String,
    var description: String,
    var questions: List<Question>,
    @SerializedName("questions_count") var questionsCount: Int
)