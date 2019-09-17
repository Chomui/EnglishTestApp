package chi.englishtest.com.model.net.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LogInResponse(
    var id: Int? = null,
    var email: String? = null,
    @SerializedName("authentication_tokens") var authenticationTokens: List<AuthenticationTokens>? = null,
    var isTeacher: Boolean? = null
)