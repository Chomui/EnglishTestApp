package chi.englishtest.com.model

import com.google.gson.annotations.SerializedName

data class Error(@SerializedName("message") var message: String)