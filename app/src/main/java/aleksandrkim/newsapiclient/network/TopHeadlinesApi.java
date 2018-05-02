package aleksandrkim.newsapiclient.network;

import aleksandrkim.newsapiclient.database.models.TopHeadlinesResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aleksandr Kim on 02 May, 2018 9:25 PM for Newsapiclient
 */

public interface TopHeadlinesApi {

    @GET("top-headlines")
    Single<TopHeadlinesResponse> getBy(@Query("country") String country, @Query("category") String category, @Query("q") String phrase);

    @GET("top-headlines")
    Single<TopHeadlinesResponse> getAll(@Query("language") String language);


//    @GET("top-headlines")
//    Single<TopHeadlinesResponse> getByPhrase(@Query("q") String phrase);
//
//    @GET("top-headlines")
//    Single<TopHeadlinesResponse> getByCountry(@Query("country") String country);
//
//    @GET("top-headlines")
//    Single<TopHeadlinesResponse> getByCategory(@Query("category") String category);
//
//    @GET("top-headlines")
//    Single<TopHeadlinesResponse> getBy(@Query("country") String country, @Query("category") String category);
//
//    @GET("top-headlines")
//    Single<TopHeadlinesResponse> getBy(@Query("country") String country, @Query("category") String category, @Query("q") String phrase);

}
