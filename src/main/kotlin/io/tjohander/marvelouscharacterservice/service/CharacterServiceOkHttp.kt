package io.tjohander.marvelouscharacterservice.service

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Metrics
import io.tjohander.marvelouscharacterservice.enum.MarvelApiUrls
import io.tjohander.marvelouscharacterservice.enum.MarvelQueryParam
import io.tjohander.marvelouscharacterservice.model.api.Character
import io.tjohander.marvelouscharacterservice.model.api.Comic
import io.tjohander.marvelouscharacterservice.model.api.DataWrapper
import io.tjohander.marvelouscharacterservice.repository.CharacterRepository
import io.tjohander.marvelouscharacterservice.util.MarvelAuthGenerator
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.Instant
import java.util.*

@Service
@Primary
class CharacterServiceOkHttp(
    private val client: OkHttpClient,
    @Value("\${marvel-api.public-key}") val marvelApiPublicKey: String,
    @Value("\${marvel-api.private-key}") val marvelApiPrivateKey: String,
    @Autowired private val characterRepository: CharacterRepository,
    private val meterRegistry: MeterRegistry
) : ICharacterService {

    private val apiCallsCounter = Metrics.counter("marvel.api.calls.count")

    private fun getCharacterFromMarvel(startsWithString: String): Character? {
        val auth = MarvelAuthGenerator.buildAuthString(
            Instant.now(),
            marvelApiPublicKey,
            marvelApiPrivateKey)
        val url = HttpUrl.Builder()
            .scheme(MarvelApiUrls.SCHEME.value)
            .host(MarvelApiUrls.HOST.value)
            .addPathSegment(MarvelApiUrls.API_VERSION.value)
            .addPathSegment(MarvelApiUrls.PUBLIC.value)
            .addPathSegment(MarvelApiUrls.CHARACTER_PATH.value)
            .addQueryParameter(MarvelQueryParam.NAME_MATCH.value, startsWithString)
            .addQueryParameter(MarvelQueryParam.TIMESTAMP.value, auth.ts)
            .addQueryParameter(MarvelQueryParam.API_KEY.value, auth.publicKey)
            .addQueryParameter(MarvelQueryParam.HASH.value, auth.md5Hash)
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            apiCallsCounter.increment()
            if (!response.isSuccessful) throw IOException("Error!: $response")
            println(response.headers.toMultimap().entries)
            val moshi = Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                .addLast(KotlinJsonAdapterFactory()).build()
            val type = Types.newParameterizedType(DataWrapper::class.java, Character::class.java, Comic::class.java)
            val jsonAdapter: JsonAdapter<DataWrapper<Character>> = moshi.adapter(type)
            val character: Character? = jsonAdapter.fromJson(response.body!!.string())?.data?.results?.firstOrNull()
            return character?.let { it ->
                characterRepository.save(it)
            }
        }
    }

    override fun getCharacterStartsWith(startsWithString: String): Character? =
        characterRepository.findCharacterByNameIs(startsWithString) ?: getCharacterFromMarvel(startsWithString)
}