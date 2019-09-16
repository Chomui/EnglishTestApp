package chi.englishtest.com.model.net.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationTokens(
    var id: Int? = null,
    var token: String? = null,
    @SerializedName("user_id") var userId: Int? = null
)