import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.kodein.db.DB
import org.kodein.db.OpenPolicy
import org.kodein.db.Options
import org.kodein.db.impl.open
import org.kodein.db.model.Id
import org.kodein.db.model.orm.Serializer
import org.kodein.db.model.unaryPlus
import org.kodein.db.orm.kotlinx.KotlinxSerializer
import org.kodein.memory.io.ReadBuffer
import org.kodein.memory.io.ReadMemory
import org.kodein.memory.io.Writeable
import org.kodein.memory.text.putString
import org.kodein.memory.text.readString
import kotlin.reflect.KClass

fun main(args: Array<String>) {
    println("Hello World!")

    val dbDirectory = "C:\\db\\Test"
    val db = DB.open(dbDirectory,
        KotlinxSerializer {
            +ReferenceDataResult.ReferenceDataResultSerializer
        },
        OpenPolicy.OpenOrCreate
    )
    println("Opened DB!")

}

data class ReferenceDataResult(
    @Id
    val id: String,

    val expiresOn: Long,

    val referenceDataType: String,

    val value: JsonObject
) {
    object ReferenceDataResultSerializer : Serializer<ReferenceDataResult> {

        override fun deserialize(
            type: KClass<out ReferenceDataResult>,
            transientId: ReadMemory,
            input: ReadBuffer,
            vararg options: Options.Read
        ): ReferenceDataResult {
            val id = input.readString()
            val expiresOn = input.readLong()
            val referenceDataType = input.readString()
            val value = Json.decodeFromString<JsonObject>(input.readString())

            return ReferenceDataResult(id, expiresOn, referenceDataType, value)
        }

        override fun serialize(model: ReferenceDataResult, output: Writeable, vararg options: Options.Write) {
            output.putString(model.id)
            output.putLong(model.expiresOn)
            output.putString(model.referenceDataType)
            output.putString(model.value.toString())
        }
    }
}