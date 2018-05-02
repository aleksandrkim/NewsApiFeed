package aleksandrkim.newsapiclient.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandr Kim on 02 May, 2018 9:59 PM for Newsapiclient
 */

public class NetworkModule {

    private static NetworkModule instance;
    private TopHeadlinesApi topHeadlinesApi;
    private Retrofit retrofit;

    public static NetworkModule getInstance() {
        if (instance == null)
            instance = new NetworkModule();

        return instance;
    }

    public NetworkModule () {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().addHeader("Authorization", Constants.apiKey).build();
                    return chain.proceed(request);
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        topHeadlinesApi = retrofit.create(TopHeadlinesApi.class);
    }

    public TopHeadlinesApi getTopHeadlinesApi () {
        return topHeadlinesApi;
    }

}
