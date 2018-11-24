package com.nulp.labs_aplication.app.main.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager;
    private ScrollToBottomListener mListener;

    private int mPreviousTotal = 0;
    private boolean mLoading = true;

    public EndlessScrollListener(LinearLayoutManager linearLayoutManager,
                                 ScrollToBottomListener listener) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.mListener = listener;
    }

    public void onRefresh () {
        mPreviousTotal = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 3;
        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            this.mListener.onScrollToBottom();
            mLoading = true;
        }
    }

    public interface ScrollToBottomListener {

        void onScrollToBottom();

    }
}
