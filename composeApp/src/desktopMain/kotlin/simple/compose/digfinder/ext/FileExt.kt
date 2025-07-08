package simple.compose.digfinder.ext

import java.io.File
import java.security.MessageDigest

fun File.hashStr(algorithm: String = "SHA-256"): String {
    val digest = MessageDigest.getInstance(algorithm)
    val bytes = this.readBytes()
    val hashBytes = digest.digest(bytes)
    return hashBytes.joinToString("") { "%02x".format(it) }
}