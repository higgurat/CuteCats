package org.kpaikena.cutecats.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.kpaikena.cutecats.R;

public class ErrorHelper {

    public static void handleNetworkError(@NonNull Context context) {
        Toast.makeText(context, R.string.network_error, Toast.LENGTH_LONG).show();
    }
}
