package org.kpaikena.cutecats.network.json;

import com.google.gson.annotations.SerializedName;

public class VoteResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("message")
    public String message;
}
