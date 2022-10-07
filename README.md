# SkipWrongTypeAdapterFactory
This class used to: Ignore Errors found in JSON Parsing due to wrong data type.

### How to use

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

Done!

### Feedback
Please review and share your feedback/suggestions.

ThankYou.
