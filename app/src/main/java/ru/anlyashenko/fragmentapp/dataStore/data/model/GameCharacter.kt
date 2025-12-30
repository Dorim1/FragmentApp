package ru.anlyashenko.fragmentapp.dataStore.data.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class GameCharacter(
    val nickname: String = "",
    val level: Int = 1,
    val inventory: List<String> = listOf(),
    val stats: Stats = Stats(),
)
@Serializable
data class Stats(
    val hp: Int = 10,
    val stamina: Int = 10,
    val mana: Int = 10,
)

object CharacterSerializer : Serializer<GameCharacter> {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = false
    }

    override val defaultValue: GameCharacter
        get() = GameCharacter()

    override suspend fun readFrom(input: InputStream): GameCharacter =
        try {
            json.decodeFromString<GameCharacter>(
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read Setting", e)
        } catch (e: IOException) {
            throw CorruptionException("Cannot read input stream", e)
        }

    override suspend fun writeTo(
        t: GameCharacter,
        output: OutputStream
    ) {
        output.write(
            json.encodeToString(t).encodeToByteArray()
        )
    }
}
