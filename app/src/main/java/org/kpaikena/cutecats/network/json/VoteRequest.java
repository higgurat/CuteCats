package org.kpaikena.cutecats.network.json;

import com.google.gson.annotations.SerializedName;

public class VoteRequest {

    @SerializedName("image_id")
    public String imageId;

    @SerializedName("sub_id")
    public String userId;

    @SerializedName("value")
    public int value;

    public VoteRequest(String imageId, String userId, int value) {
        this.imageId = imageId;
        this.userId = userId;
        this.value = value;
    }
}
