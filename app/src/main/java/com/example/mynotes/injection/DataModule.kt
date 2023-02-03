package com.example.mynotes.injection

import android.content.Context
import com.crocodic.core.data.CoreSession
import com.crocodic.core.helper.okhttp.SSLTrust
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseObserver
import com.example.mynotes.data.AppDatabase
import com.example.mynotes.data.constant.Cons
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.intuit.sdp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

@InstallIn(SingletonComponent::class)
@Module
class DataModule () {

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

    @Provides
    fun provideAppDatabse(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    fun provideGson() = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create()

    @Provides
    fun provideSession(@ApplicationContext context: Context) = CoreSession(context)

    @Provides
    fun provideOkHttpClient(session : CoreSession): OkHttpClient{

        val unSafeTrustManager = SSLTrust().createUnsafeTrustManager()
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(unSafeTrustManager),null)

        val okHttpClient = OkHttpClient().newBuilder()
            .sslSocketFactory(sslContext.socketFactory, unSafeTrustManager)
            .connectTimeout(90,TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90,TimeUnit.SECONDS)

            .addInterceptor {  chain ->
                val original = chain.request()
                val token = session.getString(Cons.TOKEN.API_TOKEN)
                val requestBuilder = original.newBuilder()
                    .header("Authorization", token)
                    .header("Content-Type","application/json")
                    .header("platform","android")
                    .method(original.method,original.body)

                val request = requestBuilder.build()
                chain.proceed(request)
            }


        if (BuildConfig.DEBUG){
            val interceptors = HttpLoggingInterceptor()
            interceptors.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(interceptors)
        }
        return okHttpClient.build()
    }

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService{
        return Retrofit.Builder()
            .baseUrl("http://34.128.80.67/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build().create(ApiService::class.java)

    }

    @Provides
    fun provideBaseObserver(apiService: ApiService,session: CoreSession): BaseObserver = BaseObserver(apiService,session)


}