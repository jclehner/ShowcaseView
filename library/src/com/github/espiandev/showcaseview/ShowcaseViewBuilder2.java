package com.github.espiandev.showcaseview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.espiandev.showcaseview.ShowcaseView.ConfigOptions;
import com.github.espiandev.showcaseview.ShowcaseView.OnShowcaseEventListener;

public class ShowcaseViewBuilder2 {

    private final ConfigOptions mOptions = new ConfigOptions();

    private ShowcaseView mSv = null;

    private Context mContext;

    private String mTitle;
    private String mSubtext;

    private int mShowcaseItemType = -1;
    private int mShowcaseActionItemId = -1;
    private Activity mShowcaseActivity;

    private View mShowcaseView;
    private int mShowcaseViewId = -1;
    private float mShowcaseX = -1;
    private float mShowcaseY = -1;

    private float mIndicatorScale = -1;

    private float mAnimStartX = -1;
    private float mAnimStartY = -1;
    private float mAnimEndX = -1;
    private float mAnimEndY = -1;
    private boolean mAnimIsRelative = false;

    private OnShowcaseEventListener mListener;

    public ShowcaseViewBuilder2(Context context) {
        mContext = context;
    }

    public ShowcaseViewBuilder2 setText(int titleResId, int subTextResId) {
        mTitle = mContext.getString(titleResId);
        mSubtext = mContext.getString(subTextResId);
        return this;
    }

    public ShowcaseViewBuilder2 setInsert(int insert) {
       mOptions.insert = insert;
       return this;
    }

    public ShowcaseViewBuilder2 setShotType(int shotType) {
        mOptions.shotType = shotType;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcaseId(int showcaseId) {
        mOptions.showcaseId = showcaseId;
        return this;
    }

    public ShowcaseViewBuilder2 setBlock(boolean block) {
        mOptions.block = block;
        return this;
    }

    public ShowcaseViewBuilder2 setHideOnClickOutside(boolean hideOnClickOutside) {
        mOptions.hideOnClickOutside = hideOnClickOutside;
        return this;
    }

    public ShowcaseViewBuilder2 setFadeInDuration(int fadeInDuration) {
        mOptions.fadeInDuration = fadeInDuration;
        return this;
    }

    public ShowcaseViewBuilder2 setFadeOutDuration(int fadeOutDuration) {
        mOptions.fadeOutDuration = fadeOutDuration;
        return this;
    }

    public ShowcaseViewBuilder2 setButtonLayoutParams(RelativeLayout.LayoutParams params) {
        mOptions.buttonLayoutParams = params;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcaseIndicatorScale(float scale) {
        mIndicatorScale = scale;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcaseView(View view) {
        mShowcaseView = view;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcaseView(int id, Activity activity) {
        mShowcaseViewId = id;
        mShowcaseActivity = activity;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcasePosition(float x, float y) {
        mShowcaseX = x;
        mShowcaseY = y;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcaseItem(int itemType, int itemId, Activity activity) {
        mShowcaseItemType = itemType;
        mShowcaseActionItemId = itemId;
        mShowcaseActivity = activity;
        return this;
    }

    public ShowcaseViewBuilder2 setAnimatedGesture(float startX, float startY, float endX, float endY) {
        mAnimStartX = startX;
        mAnimStartY = startY;
        mAnimEndX = endX;
        mAnimEndY = endY;
        mAnimIsRelative = false;
        return this;
    }

    public ShowcaseViewBuilder2 setRelativeAnimatedGesture(float lengthX, float lengthY) {
        mAnimStartX = 0;
        mAnimStartY = 0;
        mAnimEndX = lengthX;
        mAnimEndY = lengthY;
        mAnimIsRelative = true;
        return this;
    }

    public ShowcaseViewBuilder2 setShowcaseEventListener(OnShowcaseEventListener l) {
        mListener = l;
        return this;
    }

    public ShowcaseView build() {
        if (mSv == null) {
            mSv = new ShowcaseView(mContext);

            mSv.setConfigOptions(mOptions);

            if (mTitle != null || mSubtext != null) {
                mSv.setText(mTitle, mSubtext);
            }

            if (mShowcaseView != null) {
                mSv.setShowcaseView(mShowcaseView);
            } else if (mShowcaseItemType != -1 && mShowcaseActionItemId != -1) {
                mSv.setShowcaseItem(mShowcaseItemType, mShowcaseActionItemId, mShowcaseActivity);
            } else if (mShowcaseX != -1 && mShowcaseY != -1) {
                mSv.setShowcasePosition(mShowcaseX, mShowcaseY);
            } else if (mShowcaseViewId != -1) {
                mSv.addOnShowcaseEventListener(new OnShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewShow(final ShowcaseView showcaseView)
                    {
                        showcaseView.post(new Runnable() {

                            @Override
                            public void run() {
                                View v = mShowcaseActivity.findViewById(mShowcaseViewId);
                                if (v != null) {

                                    Rect rect = new Rect();
                                    v.getWindowVisibleDisplayFrame(rect);

                                    int[] location = new int[2];
                                    v.getLocationOnScreen(location);

                                    if (rect.contains(location[0], location[1])) {
                                        showcaseView.setShowcaseView(v);
                                    } else {
                                        showcaseView.setShowcaseNoView();
                                        Log.w("ShowcaseViewBuilder2", "requested View is outside visible area (" + rect + ")");
                                    }

                                } else {
                                    Log.w("ShowcaseViewBuilder2", "requested View not found in Activity");
                                }
                            }
                        });
                    }

                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {}
                });
            } else {
                mSv.setShowcaseNoView();
            }

            if (mIndicatorScale != -1) {
                mSv.setShowcaseIndicatorScale(mIndicatorScale);
            }

            if (mAnimStartX != -1 || mAnimStartY != -1 || mAnimEndX != -1 || mAnimEndY != -1) {
                mSv.addOnShowcaseEventListener(new OnShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewShow(final ShowcaseView showcaseView)
                    {
                        showcaseView.post(new Runnable() {

                            @Override
                            public void run() {
                                if (!mAnimIsRelative) {
                                    showcaseView.animateGesture(mAnimStartX, mAnimStartY, mAnimEndX, mAnimEndY);
                                } else {
                                    float startX = mAnimStartX + showcaseView.getShowcaseX();
                                    float startY = mAnimStartY + showcaseView.getShowcaseY();
                                    float endX = mAnimEndX + showcaseView.getShowcaseX();
                                    float endY = mAnimEndY + showcaseView.getShowcaseY();
                                    showcaseView.animateGesture(startX, startY, endX, endY);
                                }
                            }
                        });
                    }

                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                    }
                });
            }

            if (mListener != null) {
                mSv.addOnShowcaseEventListener(mListener);
            }
        }

        return mSv;
    }

    public void show() {
        build().show();
    }
}
