package com.github.espiandev.showcaseview;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.espiandev.showcaseview.ShowcaseView.ConfigOptions;

public class ShowcaseViewBuilder2 {

    private final ConfigOptions mOptions = new ConfigOptions();
    
    private ShowcaseView mSv = null;
    
    private Activity mActivity;
    
    private String mTitle;
    private String mSubtext;
        
    private int mShowcaseItemType = -1;
    private int mShowcaseActionItemId = -1;
    
    private View mShowcaseView;
    private float mShowcaseX = -1;
    private float mShowcaseY = -1;
    
    private float mIndicatorScale = -1;
    
    private int mStyle = -1;
    
    public ShowcaseViewBuilder2(Activity activity) {
        mActivity = activity;
    }
    
    public ShowcaseViewBuilder2 setText(int titleResId, int subTextResId) {
        mTitle = mActivity.getString(titleResId);
        mSubtext = mActivity.getString(subTextResId);
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
    
    public ShowcaseViewBuilder2 setShowcasePosition(float x, float y) {
        mShowcaseX = x;
        mShowcaseY = y;
        return this;
    }
    
    public ShowcaseViewBuilder2 setShowcaseItem(int itemType, int itemId) {
        mShowcaseItemType = itemType;
        mShowcaseActionItemId = itemId;
        return this;
    }
    
    public ShowcaseView build() {
        if (mSv == null) {
            if (mStyle != -1) {
                mSv = new ShowcaseView(mActivity, null, mStyle);
            } else {
                mSv = new ShowcaseView(mActivity);
            }
            
            mSv.setConfigOptions(mOptions);
            
            if (mTitle != null || mSubtext != null) {
                mSv.setText(mTitle, mSubtext);
            }
            
            if (mShowcaseView != null) {
                mSv.setShowcaseView(mSv);
            } else if (mShowcaseItemType != -1 && mShowcaseActionItemId != -1) {
                mSv.setShowcaseItem(mShowcaseItemType, mShowcaseActionItemId, mActivity);
            } else if (mShowcaseX != -1 && mShowcaseY != -1) {
                mSv.setShowcasePosition(mShowcaseX, mShowcaseY);
            } else {
                mSv.setShowcaseNoView();
            }
            
            if (mIndicatorScale != -1) {
                mSv.setShowcaseIndicatorScale(mIndicatorScale);
            }
        }
        
        return mSv;        
    }
}
