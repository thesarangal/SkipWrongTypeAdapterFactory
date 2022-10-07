import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * This class used to:
 * Ignore Errors found in JSON Parsing due to wrong data type.
 *
 * @author Rajat Sarangal
 * @since July 4, 2022
 * @link https://github.com/thesarangal/SkipWrongTypeAdapterFactory
 * */
internal class SkipWrongTypeAdapterFactory : TypeAdapterFactory {

    /**
     * Returns a type adapter for {@code type}, or null if this factory doesn't
     * support {@code type}.
     */
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
        val delegate = gson.getDelegateAdapter(this, type)
        return createCustomTypeAdapter(delegate)
    }

    /**
     * @return Custom [TypeAdapter] Class Object
     *
     * @param delegate Object of [TypeAdapter] to Read/Write JSON Form
     * */
    private fun <T> createCustomTypeAdapter(delegate: TypeAdapter<T>): TypeAdapter<T> {
        return object : TypeAdapter<T>() {

            /**
             * Writes one JSON value (an array, object, string, number, boolean or null)
             * for {@code value}.
             *
             * @param value the Java object to write. May be null.
             */
            @Throws(IOException::class)
            override fun write(out: JsonWriter?, value: T) {
                delegate.write(out, value)
            }

            /**
             * Reads one JSON value (an array, object, string, number, boolean or null)
             * and converts it to a Java object. Returns the converted object.
             *
             * @return the converted Java object. May be null.
             */
            @Throws(IOException::class)
            override fun read(`in`: JsonReader): T? {
                return try {
                    delegate.read(`in`)
                } catch (e: java.lang.Exception) {
                    `in`.skipValue()
                    null
                }
            }
        }
    }
}
