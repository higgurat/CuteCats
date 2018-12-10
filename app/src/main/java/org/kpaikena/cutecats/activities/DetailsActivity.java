package org.kpaikena.cutecats.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import org.kpaikena.cutecats.R;
import org.kpaikena.cutecats.network.ImageLoader;
import org.kpaikena.cutecats.network.NetworkListener;
import org.kpaikena.cutecats.network.NetworkManager;
import org.kpaikena.cutecats.network.json.CatImage;
import org.kpaikena.cutecats.utils.ErrorHelper;

public class DetailsActivity extends AppCompatActivity {

    private String mImageId;
    private ImageView mImageView;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);

        mImageView = findViewById(R.id.imageview_cat);
        mImageId =  getIntent().getStringExtra(ActivityConstants.INTENT_EXTRA_IMAGE_ID);

        setupVoteButtons();
        loadImage(mImageId);
    }

    private void setupVoteButtons() {

        mRadioGroup = findViewById(R.id.radioGroupVote);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.button_thumbs_up) {
                    vote(true);
                } else {
                    vote(false);
                }
            }
        });
    }

    /**
     * Make network call to get full image
     */
    private void loadImage(String imageId) {
        NetworkManager.getCatImageById(imageId, new NetworkListener<CatImage>() {
            @Override
            public void onSuccess(@NonNull CatImage data) {
                ImageLoader.loadImageFromUrlIntoView(data.url, mImageView);

                // Enable vote buttons
                mRadioGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mImageView.setImageResource(R.drawable.placeholder);
                ErrorHelper.handleNetworkError(getApplicationContext());
            }
        });
    }

    /**
     * Make Network call to vote for image
     * @param vote your vote(like/dislike)
     */
    private void vote(boolean vote) {
        NetworkManager.vote(mImageId, vote);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
