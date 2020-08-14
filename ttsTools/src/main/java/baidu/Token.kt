package baidu

import com.google.gson.Gson
import common.Holder
import common.Log
import common.urlEncode
import okhttp3.Request

object Token {

    //  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private const val appKey = "WygunXu3Ut7Zi3kjkXprvaQ4"

    // 填写网页上申请的APP SECRET 如 $secretKey="94dc99566550d87f8fa8ece112xxxxx"
    private const val secretKey = "DSQVtrAzneLnQwGyheHsaa8jGH3VBc0j"

    /**
     * URL , Token的url，http可以改为https
     */
    private const val URL = "http://openapi.baidu.com/oauth/2.0/token"

    private var accessToken: AccessToken? = null

    fun refresh() {
        val tokenUrl =
            "$URL?grant_type=client_credentials&client_id=${appKey.urlEncode()}&client_secret=${secretKey.urlEncode()}"
        val request = Request.Builder().url(tokenUrl).build()
        val call = Holder.okhttp.newCall(request)
        val response = call.execute()
        if (response.code() == 200) {
            val body = response.body()
            if (body != null) {
                val tt = Gson().fromJson<AccessToken>(body.string(), AccessToken::class.java)
                accessToken = tt
            }
        } else {
            Log.d(
                "okhttp",
                "token refresh failed with code = ${response.code()}, message = ${response.body()?.string()}"
            )
        }
    }

    fun getToken(): String? = accessToken?.access_token

    data class AccessToken(val access_token: String, val expires_in: Long) {
        val refresh_token: String? = null
        val scope: String? = null
        val session_key: String? = null
        val session_secret: String? = null
    }

}