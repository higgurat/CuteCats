package org.kpaikena.cutecats.network;

import org.kpaikena.cutecats.network.json.CatImage;
import org.kpaikena.cutecats.network.json.VoteRequest;
import org.kpaikena.cutecats.network.json.VoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit web service to load data from thecatapi.com
 */
public interface CatService {

    @GET("v1/images/search?size=med&mime_types=jpg,png&format=json&order=ASC&limit=2")
    Call<List<CatImage>> getCatImages(@Query("page") int page, @Query("sub_id") String subId, @Header("x-api-key") String apiKey);

    @GET("v1/images/{image_id}?size=full&include_vote=true")
    Call<CatImage> getCatImageById(@Path("image_id") String imageId, @Query("sub_id") String subId, @Header("x-api-key") String apiKey);

    @POST("v1/votes")
    Call<VoteResponse> voteForCatImage(@Body VoteRequest requestBody, @Header("x-api-key") String apiKey);
}
