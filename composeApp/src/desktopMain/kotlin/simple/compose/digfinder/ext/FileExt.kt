package simple.compose.digfinder.ext

import java.io.File
import java.security.MessageDigest

fun File.hashStr(algorithm: String = "SHA-256"): String {
    val digest = MessageDigest.getInstance(algorithm)
    val bytes = this.readBytes()
    val hashBytes = digest.digest(bytes)
    return hashBytes.joinToString("") { "%02x".format(it) }
}

val Long.formatStr
    get() = if (this < 1024) {
        "$this B"
    } else if (this < 1024 * 1024) {
        "${this / 1024} KB"
    } else {
        "${this / 1024 / 1024} MB"
    }