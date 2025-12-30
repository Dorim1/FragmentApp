package ru.anlyashenko.fragmentapp.dataStore.data.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import ru.anlyashenko.fragmentapp.dataStore.AchievementStore
import java.io.InputStream
import java.io.OutputStream

object AchievementStoreSerializer : Serializer<AchievementStore> {
    override val defaultValue: AchievementStore
        get() = AchievementStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AchievementStore {
        try {
            return AchievementStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto." , exception)
        }
    }

    override suspend fun writeTo(
        t: AchievementStore,
        output: OutputStream
    ) {
        t.writeTo(output)
    }

}