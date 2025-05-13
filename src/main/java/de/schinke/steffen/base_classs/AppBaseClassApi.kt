package de.schinke.steffen.base_classs

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

abstract class AppBaseClassApi(

    private val baseUrl: String = "https://fakestoreapi.com/"
) {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    protected val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        //.addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // fall: wenn resonse success ok aber body leer ist dann kommt ein null pointer exception,
    // weil moshi beim zugriff auf body selbst bei der prüfung != null automatisch gleich parst
    // und deswegen der null pointer exception ausgelöst wird
//    private class NullOnEmptyConverterFactory : Converter.Factory() {
//
//        override fun responseBodyConverter(
//
//            type: Type,
//            annotations: Array<out Annotation>,
//            retrofit: Retrofit
//        ): Converter<ResponseBody, *> {
//
//            val next = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
//            return Converter<ResponseBody, Any?> { body ->
//                if (body.contentLength() == 0L) null else next.convert(body)
//            }
//        }
//    }
}