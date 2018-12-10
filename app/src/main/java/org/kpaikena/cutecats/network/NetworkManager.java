package org.kpaikena.cutecats.network;

import android.support.annotation.NonNull;
import android.util.Log;

import org.kpaikena.cutecats.network.json.CatImage;
import org.kpaikena.cutecats.network.json.VoteRequest;
import org.kpaikena.cutecats.network.json.VoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.kpaikena.cutecats.network.RetrofitClientInstance.getRetrofitInstance;

/**
 * Helper class to load data from API service
 */
public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    /**
     * Loads cat images from API service
     *
     * @param page      Page number for pagination purposes
     * @param listener  Instance of {@link NetworkListener} to notify results
     */
    public static void loadCatImages(int page, @NonNull final NetworkListener<List<CatImage>> listener) {

        getApiService().getCatImages(page,  ApiConstants.CAT_API_USER_ID, ApiConstants.CAT_API_KEY).enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatImage>> call, @NonNull Response<List<CatImage>> response) {
                Log.d(TAG, "loadCatImages()- Received API response");

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<CatImage>> call, @NonNull Throwable throwable) {
                Log.e(TAG, "loadCatImages()- Network error");

                listener.onFailure(throwable);
            }
        });
    }

    public static void getCatImageById(@NonNull String imageId, @NonNull final NetworkListener<CatImage> listener) {
        getApiService().getCatImageById(imageId, ApiConstants.CAT_API_USER_ID, ApiConstants.CAT_API_KEY).enqueue(new Callback<CatImage>() {
            @Override
            public void onResponse(@NonNull Call<CatImage> call, @NonNull Response<CatImage> response) {
                Log.d(TAG, "getCatImageById()- Received API response");

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CatImage> call, @NonNull Throwable throwable) {
                Log.e(TAG, "getCatImageById()- Network error");

                listener.onFailure(throwable);
            }
        });
    }

    /**
     * Vote for image (like/dislike)
     * @param imageId ID of the image
     * @param vote    Boolean vote(true for like, false for dislike)
     */
    public static void vote(@NonNull String imageId, boolean vote) {

        VoteRequest voteRequest = new VoteRequest(imageId, ApiConstants.CAT_API_USER_ID, vote ? 1 : 0);

        getApiService().voteForCatImage(voteRequest, ApiConstants.CAT_API_KEY).enqueue(new Callback<VoteResponse>() {
            @Override
            public void onResponse(@NonNull Call<VoteResponse> call, @NonNull Response<VoteResponse> response) {
                Log.d(TAG, "vote()- Received API response:" + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<VoteResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "vote()- Network error");
            }
        });

    }

    private static CatService getApiService() {
        return getRetrofitInstance().create(CatService.class);
    }
}
