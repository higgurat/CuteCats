package org.kpaikena.cutecats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import org.kpaikena.cutecats.R;
import org.kpaikena.cutecats.adapters.RecyclerViewImageAdapter;
import org.kpaikena.cutecats.network.NetworkListener;
import org.kpaikena.cutecats.network.NetworkManager;
import org.kpaikena.cutecats.network.json.CatImage;
import org.kpaikena.cutecats.ui.InfiniteRecyclerViewScrollListener;
import org.kpaikena.cutecats.utils.ErrorHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NUMBER_OF_COLUMNS = 2;

    private RecyclerViewImageAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private InfiniteRecyclerViewScrollListener mScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate()");

        setupCatGrid();

        // Load the first page of cat images
        loadCatImages(0);
    }

    private void setupCatGrid() {
        // Setup recycler view with staggered grid layout (to accommodate different sized images in a grid)
        mRecyclerView = findViewById(R.id.rv_cats);

        mGridLayoutManager = new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mAdapter = new RecyclerViewImageAdapter();
        mAdapter.setItemClickListener(new RecyclerViewImageAdapter.ItemClickListener() {
            @Override
            public void onItemClick(CatImage catImage) {
                openDetailsActivity(catImage);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Setup infinite scroll listener
        mScrollListener = new InfiniteRecyclerViewScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMoreDataRequested(int pageNumber) {
                loadCatImages(pageNumber);
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    private void openDetailsActivity(CatImage catImage) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(ActivityConstants.INTENT_EXTRA_URL, catImage.getUrl());
        intent.putExtra(ActivityConstants.INTENT_EXTRA_IMAGE_ID, catImage.getId());
        startActivity(intent);
    }

    private void loadCatImages(int page) {
        NetworkManager.loadCatImages(page, new NetworkListener<List<CatImage>>() {
            @Override
            public void onSuccess(List<CatImage> data) {
                updateCatGrid(data);
            }

            @Override
            public void onFailure(Throwable throwable) {
                ErrorHelper.handleNetworkError(getApplicationContext());
            }
        });
    }

    private void updateCatGrid(List<CatImage> catImageList) {

         mAdapter.addUrls(catImageList);
         mAdapter.notifyDataSetChanged();
    }
}
