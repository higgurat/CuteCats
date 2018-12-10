package org.kpaikena.cutecats.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.kpaikena.cutecats.R;
import org.kpaikena.cutecats.network.ImageLoader;
import org.kpaikena.cutecats.network.json.CatImage;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for cat pictures
 */
public class RecyclerViewImageAdapter extends RecyclerView.Adapter<RecyclerViewImageAdapter.ViewHolder> {

    private List<CatImage> mCatImages;

    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onItemClick(CatImage item);
    }

    public RecyclerViewImageAdapter() {
        super();

        mCatImages = new ArrayList<>();
    }

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void clear() {
        mCatImages = new ArrayList<>();
    }

    public void addUrls(List<CatImage> urls) {
        mCatImages.addAll(urls);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mCatImages.get(i), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCatImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageview_cat);
        }

        public void bind(final CatImage item, final ItemClickListener listener) {
            ImageLoader.loadImageFromUrlIntoView(item.url, mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
