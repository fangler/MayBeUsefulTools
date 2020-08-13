import okhttp3.OkHttpClient
import okhttp3.Response
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

fun urlEncode(text: String): String {
    return URLEncoder.encode(text, "UTF-8")
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