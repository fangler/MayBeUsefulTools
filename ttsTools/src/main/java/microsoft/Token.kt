package microsoft

import common.Holder
import common.Log
import common.base64Encode
import okhttp3.Headers
import okhttp3.Request

object Token {
    //FIXME 更正这个值
    const val YOUR_SUBSCRIPTION_KEY = ""

    //有效时间10分钟
    fun refresh(): String? {
        return try {
            val URL = "https://eastasia.api.cognitive.microsoft.com/sts/v1.0/issueToken"
            val request = Request.Builder().url(URL).headers(
                Headers.of(
                    mapOf(
                        "Content-type" to "application/x-www-form-urlencoded",
                        "Content-Length" to "0",
                        "Ocp-Apim-Subscription-Key" to YOUR_SUBSCRIPTION_KEY
                    )
                )
            ).build()
            val call = Holder.okhttp.newCall(request)
            val response = call.execute()
            return if (response.code() == 200) {
                response.body()?.string()
            } else null
        } catch (e: Exception) {
            Log.d("Token", "refresh failed Exception: $e")
            return null
        }
    }

}