package com.andview.refreshview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.andview.refreshview.utils.Utils;
import com.andview.refreshview.view.MyRoundCanvas;

import java.util.Calendar;

public class XRefreshViewHeader extends LinearLayout implements IHeaderCallBack {
    private ViewGroup mContent;
    private ImageView mArrowImageView;
    private ImageView mOkImageView;
    private ImageView refreshing_icon;
    private MyRoundCanvas roundProgressBar2;
//    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private TextView mHeaderTimeTextView;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private final int ROTATE_ANIM_DURATION = 180;

    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;

    public XRefreshViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XRefreshViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContent = (ViewGroup) LayoutInflater.from(context).inflate(
                R.layout.xrefreshview_header, this);
        mArrowImageView = (ImageView) findViewById(R.id.xrefreshview_header_arrow);
        mOkImageView = (ImageView) findViewById(R.id.xrefreshview_header_ok);
        mHintTextView = (TextView) findViewById(R.id.xrefreshview_header_hint_textview);
        mHeaderTimeTextView = (TextView) findViewById(R.id.xrefreshview_header_time);
//        mProgressBar = (ProgressBar) findViewById(R.id.xrefreshview_header_progressbar);
        refreshing_icon=(ImageView)findViewById(R.id.refreshing_icon);
        roundProgressBar2=(MyRoundCanvas)findViewById(R.id.roundProgressBar2);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating_anim);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
    }

    public void setRefreshTime(long lastRefreshTime) {
        // 获取当前时间
        Calendar mCalendar = Calendar.getInstance();
        long refreshTime = mCalendar.getTimeInMillis();
        long howLong = refreshTime - lastRefreshTime;
        int minutes = (int) (howLong / 1000 / 60);
        String refreshTimeText = null;
        Resources resources = getContext().getResources();
        if (minutes < 1) {
            refreshTimeText = resources
                    .getString(R.string.xrefreshview_refresh_justnow);
        } else if (minutes < 60) {
            refreshTimeText = resources
                    .getString(R.string.xrefreshview_refresh_minutes_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes);
        } else if (minutes < 60 * 24) {
            refreshTimeText = resources
                    .getString(R.string.xrefreshview_refresh_hours_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60);
        } else {
            refreshTimeText = resources
                    .getString(R.string.xrefreshview_refresh_days_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60 / 24);
        }
        mHeaderTimeTextView.setText(refreshTimeText);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        refreshing_icon.clearAnimation();
        refreshing_icon.setVisibility(View.GONE);
        mArrowImageView.setVisibility(View.VISIBLE);
        mOkImageView.setVisibility(View.GONE);
        mArrowImageView.startAnimation(mRotateDownAnim);
        mHintTextView.setText(R.string.xrefreshview_header_hint_normal);
        roundProgressBar2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateReady() {
        refreshing_icon.clearAnimation();
        refreshing_icon.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.GONE);
        mArrowImageView.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mRotateUpAnim);
        mHintTextView.setText(R.string.xrefreshview_header_hint_ready);
//        mHeaderTimeTextView.setVisibility(View.VISIBLE);
        mHeaderTimeTextView.setVisibility(View.GONE);
        roundProgressBar2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateRefreshing() {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.GONE);
        refreshing_icon.startAnimation(refreshingAnimation);
        refreshing_icon.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.xrefreshview_header_hint_loading);
        roundProgressBar2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStateFinish(boolean success) {
        refreshing_icon.clearAnimation();
        mArrowImageView.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.VISIBLE);
        mOkImageView.setImageResource(success ? R.drawable.xrefresh_ok : R.drawable.error);
        refreshing_icon.setVisibility(View.GONE);
        mHintTextView.setText(success ? R.string.xrefreshview_header_hint_loaded : R.string.xrefreshview_header_hint_loaded_fail);
        mHeaderTimeTextView.setVisibility(View.GONE);
        roundProgressBar2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onHeaderMove(double offset, int offsetY, int deltaY) {

    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
