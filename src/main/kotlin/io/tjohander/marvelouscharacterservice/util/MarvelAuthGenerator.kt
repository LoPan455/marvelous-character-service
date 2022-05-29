package io.tjohander.marvelouscharacterservice.util

import io.tjohander.marvelouscharacterservice.model.MarvelAuthComponents
import org.apache.commons.codec.digest.DigestUtils
import java.time.Instant

class MarvelAuthGenerator {

    companion object {
        fun buildAuthString(
            timeStamp: Instant,
            marvelApiPublicKey: String,
            marvelApiPrivateKey: String
        ): MarvelAuthComponents {
            val preHashString =
                "${timeStamp.toEpochMilli()}$marvelApiPrivateKey$marvelApiPublicKey"
            val hashed: String = DigestUtils.md5Hex(preHashString)
            return MarvelAuthComponents(
                ts = timeStamp.toEpochMilli().toString(),
                publicKey = marvelApiPublicKey,
                md5Hash = hashed
            )
        }
    }

}