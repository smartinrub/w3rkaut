package net.dynu.w3rkaut.network;

import net.dynu.w3rkaut.network.RestConverters.ToStringConverterFactory;
import net.dynu.w3rkaut.network.Services.SyncService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestClientModule {

    public static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/";

    @Provides
    public static Retrofit provideRetrofit() {
        // For debugging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    public static SyncService provideApiService(Retrofit retrofit) {
        return retrofit.create(SyncService.class);
    }
}
