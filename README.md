# SkipWrongTypeAdapterFactory

SkipWrongTypeAdapterFactory is a class in Kotlin that provides a way to ignore errors found in JSON
parsing due to wrong data types. It is particularly useful when dealing with external APIs that may
return unexpected data types.

## Usage

To use this class, simply create an instance of it and register it with your Gson instance:

```bash
    val gson = GsonBuilder()
        .registerTypeAdapterFactory(SkipWrongTypeAdapterFactory())
        .create()
```

### Example

```bash
    @Provides
    @Singleton
    fun provideGson(): Gson {

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .setLenient()

        if (!BuildConfig.DEBUG) {
            gson.registerTypeAdapterFactory(SkipWrongTypeAdapterFactory())
        }

        return gson.create()
    }
```

### How it works

Suppose you have the following JSON:

```bash
  {
   "name": "John",
   "age": "30"
  }
```

If you try to deserialize this JSON into a class with an integer age field, you will get a
JsonSyntaxException due to the age field being a string. However, if you use
SkipWrongTypeAdapterFactory, the exception will be ignored and the age field will be set to null.

```bash
  data class Person(val name: String, val age: Int)
  
  val gson = GsonBuilder()
      .registerTypeAdapterFactory(SkipWrongTypeAdapterFactory())
      .create()
  
  val json = "{\"name\": \"John\", \"age\": \"30\"}"
  val person = gson.fromJson(json, Person::class.java)
  println(person.age) // prints "null"
```

## Contributions

Contributions to this library are welcome. If you find a bug or have a feature request,
please open an issue on
the [GitHub repository](https://github.com/thesarangal/SkipWrongTypeAdapterFactory).

## License

This library is released under the [MIT License](https://opensource.org/licenses/MIT).
