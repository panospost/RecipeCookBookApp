package cz.ackee.cookbook.network

import java.io.IOException

class NoConnectivityException(message: String) : IOException(message) {
    override val message: String?
        get() = super.message
}
