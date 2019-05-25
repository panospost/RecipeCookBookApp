package cz.ackee.cookbook.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.net.ConnectivityManager






class ConnectivityInterceptor(var context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(isOnline(context)){
            val builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }else{
            throw NoConnectivityException("No connectivity")
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}