package org.kpaikena.cutecats.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView Scroll Listener implementation with infinite scrolling
 */
public abstract class InfiniteRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final int STARTING_PAGE_INDEX = 0;

    /**
     * The minimum amount of items to have below current scroll position  before loading more.
     **/
    private static final int VISIBLE_ITEM_THRESHOLD = 6;

    private int mCurrentPage = 0;
    private int mPreviousTotalItemCount = 0;
    private boolean mIsLoading = true;

    private StaggeredGridLayoutManager mLayoutManager;

    public InfiniteRecyclerViewScrollListener(final StaggeredGridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;

        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView view, int dx, int dy) {
        int totalItemCount = mLayoutManager.getItemCount();

        // Find last visible item
        int[] lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null);
        int lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);

        // If the total item count is zero and the previous isn't, assume the list is invalidated and should be reset back to initial state
        if (totalItemCount < mPreviousTotalItemCount) {
            mCurrentPage = STARTING_PAGE_INDEX;
            mPreviousTotalItemCount = totalItemCount;

            if (totalItemCount == 0) {
               mIsLoading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has changed, if so we conclude it has finished loading and update the current page number and total item count.
        if (mIsLoading && (totalItemCount > mPreviousTotalItemCount)) {
            mIsLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached the visible item threshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMoreDataRequested to fetch the data.
        if (!mIsLoading && (lastVisibleItemPosition + VISIBLE_ITEM_THRESHOLD) > totalItemCount) {
            mCurrentPage++;
            onLoadMoreDataRequested(mCurrentPage);
            mIsLoading = true;
        }
    }

    /**
     * Reset state of infinite scrolling listener. Call this method whenever performing new searches.
     */
    public void resetState() {
        mCurrentPage = STARTING_PAGE_INDEX;
        mPreviousTotalItemCount = 0;
        mIsLoading = true;
    }

    /***
     * Load more data based on next page index.
     * @param page page index for which to load data
     */
    public abstract void onLoadMoreDataRequested(int page);
}
