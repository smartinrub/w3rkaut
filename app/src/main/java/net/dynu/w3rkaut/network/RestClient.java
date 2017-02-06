package net.dynu.w3rkaut.network;

import net.dynu.w3rkaut.network.restconverters.ToStringConverterFactory;
import net.dynu.w3rkaut.network.services.SyncService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is the main entry point for network communication. Use this class for instancing REST services which do the
 * actual communication.
 *
 * @author Sergio Martin Rubio
 */

public class RestClient {
    /**
     * This is our main backend/server URL.
     */
    public static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/";

    private static Retrofit getRetrofitInstance() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static SyncService getApiService() {
        return getRetrofitInstance().create(SyncService.class);
    }
}
