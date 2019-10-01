package chi.englishtest.com.utils

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

class ServiceManager private constructor(){

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (cm != null) {
                if (Build.VERSION.SDK_INT < 23) {
                    val ni: NetworkInfo = cm.activeNetworkInfo

                    return ni != null && ni.isConnected
                }
            } else {
                val network: Network = cm.activeNetwork

                if (network != null) {
                    val nc: NetworkCapabilities = cm.getNetworkCapabilities(network)

                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ))
                }
            }

            return false
        }


        fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
            return false
        }
    }
}