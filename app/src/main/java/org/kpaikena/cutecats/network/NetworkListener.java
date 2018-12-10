package org.kpaikena.cutecats.network;

import android.support.annotation.NonNull;

public interface NetworkListener<T> {

    /**
     * Called when data loaded from remote server successfully
     */
    void onSuccess(@NonNull T data);

    /**
     * Called when data could not be loaded
     */
    void onFailure(Throwable throwable);
}
