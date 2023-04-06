package pt.isel.ps.cinescope.utils

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.services.exceptions.InternalServerErrorException
import java.security.MessageDigest
import java.util.Base64

@Component
class SHA256Encoder : Encoder {

    override fun encodeInfo(info: String): String {
        return hash(info)
    }

    override fun validateInfo(info: String, encodedInfo: String?): Boolean {//TODO("Rever exception lançada")
        if (encodedInfo.isNullOrBlank()) throw InternalServerErrorException("Something went wrong validating the password")
        return encodedInfo == hash(info)
    }

    private fun hash(input: String): String { //TODO("Rever codigo")
        val messageDigest = MessageDigest.getInstance("SHA256")
        return Base64.getUrlEncoder().encodeToString(
            messageDigest.digest(
                Charsets.UTF_8.encode(input).array()
            )
        )
    }
}