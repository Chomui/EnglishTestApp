package chi.englishtest.com.model.net.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationTokens(
    var id: Int,
    var token: String,
    @SerializedName("user_id") var userId: Int
)