package common

import okhttp3.OkHttpClient
import java.net.URLEncoder
import java.util.*
import java.util.concurrent.TimeUnit

fun String.urlEncode(): String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.base64Encode(): String {
    return String(Base64.getEncoder().encode(this.toByteArray()))
}

object Holder {
    val okhttp by lazy {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor {
                val request = it.request()
                Log.d("okhttp", "${request.method().toUpperCase()}: ${request.url()}")
                return@addInterceptor it.proceed(request)
            }
            .build()
    }
}