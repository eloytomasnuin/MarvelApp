package com.ob.data

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {

    fun md5(input: String): String {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(input.toByteArray())
            val number = BigInteger(1, messageDigest)
            var md5: String = number.toString(16)
            while (md5.length < 32) md5 = "0$md5"
            md5
        } catch (e: NoSuchAlgorithmException) {
            return ""
        }
    }
}