package com.mardesago.umatbertanya.utils;

/**
 * Created by Febrian Reza on 21-Nov-17.
 */

import android.util.Log;

import com.mardesago.umatbertanya.BuildConfig;
import com.mardesago.umatbertanya.UmatBertanyaApp;
import com.mardesago.umatbertanya.service.ArtikelService;
import com.mardesago.umatbertanya.service.ImamService;
import com.mardesago.umatbertanya.service.MazhabService;
import com.mardesago.umatbertanya.service.ReferensiService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * @author Annyce Davis
 */
public class Injector
{
    private static final String CACHE_CONTROL = "Cache-Control";

    public static Retrofit provideRetrofit (String baseUrl)
    {
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( provideOkHttpClient() )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }

    private static OkHttpClient provideOkHttpClient ()
    {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .addNetworkInterceptor( provideCacheInterceptor() )
                .cache( provideCache() )
                .build();
    }

    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( UmatBertanyaApp.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Log.e( "INJECTOR", "Could not create Cache!" );
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                        //Timber.d( message );
                    }
                } );
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? HEADERS : NONE );
        return httpLoggingInterceptor;
    }

    public static Interceptor provideCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();

                if ( !UmatBertanyaApp.hasNetwork() )
                {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }


    public static ArtikelService artikelService ()
    {
        return provideRetrofit( StaticFunction.BASE_URL ).create( ArtikelService.class );
    }

    public static ReferensiService referensiService(){
        return provideRetrofit(StaticFunction.BASE_URL).create(ReferensiService.class);
    }

    public static ImamService imamService(){
        return provideRetrofit(StaticFunction.BASE_URL).create(ImamService.class);
    }

    public static MazhabService mazhabService ()
    {
        return provideRetrofit( StaticFunction.BASE_URL ).create( MazhabService.class );
    }

}