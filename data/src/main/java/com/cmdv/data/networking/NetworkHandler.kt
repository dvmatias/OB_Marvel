package com.cmdv.data.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Utility class to perform network status validations and checks.
 *
 * @param context Required context to perform system network call validations.
 */
class NetworkHandler constructor(private val context: Context) {
    /**
     * Checks internet connection status.
     */
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}

val Context.networkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo