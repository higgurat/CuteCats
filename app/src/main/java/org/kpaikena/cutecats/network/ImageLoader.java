package org.kpaikena.cutecats.network;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kpaikena.cutecats.R;

/***
 * Helper class used for loading images from URLs
 */
public class ImageLoader {

    public static void loadImageFromUrlIntoView(@NonNull final String url, @NonNull final ImageView imageView) {
        Picasso.get().
                load(url).
                placeholder(R.drawable.placeholder).
                into(imageView);
    }
}
