package chi.englishtest.com.model.net.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogInResponse(
    var id: Int,
    var email: String,
    @SerializedName("authentication_tokens") var authenticationTokens: AuthenticationTokens,
    var isTeacher: Boolean
)