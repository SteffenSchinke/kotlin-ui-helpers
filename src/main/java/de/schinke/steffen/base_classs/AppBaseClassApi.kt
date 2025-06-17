package de.schinke.steffen.base_classs

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.schinke.steffen.interfaces.AppJwtProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

abstract class AppBaseClassApi(

    private val baseUrl: String,
    private val jwtTokenProvider: AppJwtProvider,
    private val headerOrigin: String? = null,
) {

    private val moshi: Moshi = Moshi.Builder()
        .add(LocalDateTimeFormatter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val token = jwtTokenProvider.getJwtToken()

        val requestBuilder = original.newBuilder()

        headerOrigin?.let {
            requestBuilder.addHeader("Origin", it)
        }

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        chain.proceed(requestBuilder.build())
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    protected val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private class LocalDateTimeFormatter {

        companion object {
            private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        }

        @FromJson
        fun fromJson(json: String?): LocalDateTime? {
            return json?.let {
                try {
                    LocalDateTime.parse(it, FORMATTER)
                } catch (e: DateTimeParseException) { null }
            }
        }

        @ToJson
        fun toJson(value: LocalDateTime?): String? {
            return value?.format(FORMATTER)
        }
    }
}

