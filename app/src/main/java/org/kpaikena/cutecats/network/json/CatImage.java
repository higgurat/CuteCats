package org.kpaikena.cutecats.network.json;

import com.google.gson.annotations.SerializedName;

public class CatImage {

    @SerializedName("id")
    public String id;

    @SerializedName("url")
    public String url;

    public String getId() {
        return  id;
    }

    public String getUrl() {
        return url;
    }
}
