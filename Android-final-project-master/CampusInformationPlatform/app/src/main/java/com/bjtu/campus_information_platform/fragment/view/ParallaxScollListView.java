package com.bjtu.campus_information_platform.fragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.model.Step;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;

import java.util.List;

import static com.bjtu.campus_information_platform.activity.MyApplication.getApplication;

public class ParallaxScollListView extends ListView implements OnScrollListener {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    private ImageView mImageView;
    private int mDrawableMaxHeight = -1;
    private int mImageViewHeight = -1;
    private int mDefaultImageViewHeight = 0;
	private double mZoomRatio;

    private interface OnOverScrollByListener {
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    private interface OnTouchEventListener {
        public void onTouchEvent(MotionEvent ev);
    }

    public ParallaxScollListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    public ParallaxScollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxScollListView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        mDefaultImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }

    @Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		initViewsBounds(mZoomRatio);
	}

	@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.e("ljz","onScrollStateChanged");
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = false;


        /*
        * 在这里实现网络请求刷新界面，以及刚刚打开界面进行网络请求
        */
        if(!getApplication().isSend){
            RequestParams params=new RequestParams();
            params.put("nickname","ljz");
            params.put("steps",String.valueOf(getApplication().mStep));
            if(!getApplication().isSend){
                Log.e("ljz","start request");
                HttpRequest.postStepApi(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {

                        List<Step> list = (List<Step>) responseObj;
                        getApplication().stepList.clear();
                        list.forEach(step -> {
                            getApplication().stepList.add(step.getNickname()+"  steps: "+step.getSteps());
                        });
                        getApplication().steps= (String[]) getApplication().stepList.toArray(new String[0]);
                        getApplication().isUpdate=true;
                        getApplication().isSend=false;
                        Log.e("ljz",getApplication().stepList.toString());
                        Log.e("ljz","success");
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        Log.e("ljz","failed");
                    }
                });
            }

        }






        isCollapseAnimation = scrollByListener.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent)
                || isCollapseAnimation;

        return isCollapseAnimation ? true : super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
       // Log.e("ljz","onScroll");
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //Log.e("ljz","onScrollChanged");
        if (mImageView != null) {
            View firstView = (View) mImageView.getParent();
            // firstView.getTop < getPaddingTop means mImageView will be covered by top padding,
            // so we can layout it to make it shorter
            //Log.e("ljz","onScrollChanged2");
            if (firstView.getTop() < getPaddingTop() && mImageView.getHeight() > mImageViewHeight) {
                mImageView.getLayoutParams().height = Math.max(mImageView.getHeight() - (getPaddingTop() - firstView.getTop()), mImageViewHeight);
                //Log.e("ljz","onScrollChanged3");
                // to set the firstView.mTop to 0,
                // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
                firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
                mImageView.requestLayout();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        touchListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public void setParallaxImageView(ImageView iv) {
        mImageView = iv;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void initViewsBounds(double zoomRatio) {
        if (mImageViewHeight == -1 && mImageView != null) {
            mImageViewHeight = mImageView.getHeight();
            if (mImageViewHeight <= 0) {
                mImageViewHeight = mDefaultImageViewHeight;
            }
            double ratio = ((double) mImageView.getDrawable().getIntrinsicWidth()) / ((double) mImageView.getWidth());

            mDrawableMaxHeight = (int) ((mImageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ?
                    zoomRatio : 1));
        }
    }
    
    public void setZoomRatio(double zoomRatio) {
    	mZoomRatio = zoomRatio;
    }

    private OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (mImageView != null && mImageView.getHeight() <= mDrawableMaxHeight && isTouchEvent) {
                if (deltaY < 0) {
                    if (mImageView.getHeight() - deltaY / 2 >= mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY / 2 < mDrawableMaxHeight ?
                                mImageView.getHeight() - deltaY / 2 : mDrawableMaxHeight;
                        mImageView.requestLayout();
                    }
                } else {
                    if (mImageView.getHeight() > mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY > mImageViewHeight ?
                                mImageView.getHeight() - deltaY : mImageViewHeight;
                        mImageView.requestLayout();
                        return true;
                    }
                }
            }
            return false;
        }
    };

    private OnTouchEventListener touchListener = new OnTouchEventListener() {
        @Override
        public void onTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mImageView != null && mImageViewHeight < mImageView.getHeight()) {
                    ResetAnimimation animation = new ResetAnimimation(
                            mImageView, mImageViewHeight);
                    animation.setDuration(300);
                    mImageView.startAnimation(animation);
                }
            }
        }
    };

    public class ResetAnimimation extends Animation {
        int targetHeight;
        int originalHeight;
        int extraHeight;
        View mView;

        protected ResetAnimimation(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }
}
