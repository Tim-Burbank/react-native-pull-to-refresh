package com.yunio.pulltorefresh.refresh.header;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yunio.pulltorefresh.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by JauZhou on 2018/3/21.
 */

public class DefaultRefreshHeader extends FrameLayout implements PtrUIHandler {
    /**
     * 重置
     * 准备刷新
     * 开始刷新
     * 结束刷新
     */
    public static final int STATE_RESET = -1;
    public static final int STATE_PREPARE = 0;
    public static final int STATE_BEGIN = 1;
    public static final int STATE_FINISH = 2;

    /**
     * 状态识别
     */
    private int mState;

    public DefaultRefreshHeader(Context context) {
        super(context);
        initView();
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private final static int MSG_WHAT_UPDATE = 99;
    private int curPosotion = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_WHAT_UPDATE) {
                //        Log.e("MyRefreshHeader", "curPosotion  :" + curPosotion);
                //        Log.e("MyRefreshHeader", "curPosotion + targetHeight:" + (curPosotion + targetHeight));
                if (curPosotion - targetWidth < 0) {
                    curPosotion = targetBitmapWidth - targetWidth;
                }
                Bitmap bitmap = Bitmap.createBitmap(targetBitmap, curPosotion, 0, targetWidth, targetHeight);
                bitmap = bimapRound(bitmap, targetHeight / 2);
                mIvEarthTemp.setImageBitmap(bitmap);
                curPosotion--;
                sendEmptyMessageDelayed(MSG_WHAT_UPDATE, 15);
            }
        }
    };

    private Bitmap bimapRound(Bitmap mBitmap, float index) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        canvas.drawRoundRect(rectf, index, index, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(mBitmap, rect, rect, paint);
        return bitmap;

    }

    private View mRootView;
    private ImageView mIvEarth, mIvEarthTemp;
    private Bitmap targetBitmap;
    private int targetBitmapWidth;
    private int targetWidth, targetHeight;
    boolean hasBegin = false;
	
    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_header_view, this, false);
        mIvEarth = (ImageView) mRootView.findViewById(R.id.iv_earth);
        mIvEarthTemp = (ImageView) mRootView.findViewById(R.id.iv_earth_stroke);
        addView(mRootView);
        mIvEarth.post(new Runnable() {
            @Override
            public void run() {
                Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.earth_bg);
                int srcWidth = srcBitmap.getWidth();
                int srcHeight = srcBitmap.getHeight();
                //        Log.e("MyRefreshHeader", "srcBitmap size  :" + srcWidth + " , " + srcHeight);
                //                targetWidth = mIvEarth.getHeight();
                targetWidth = getContext().getResources().getDimensionPixelSize(R.dimen.earth_ball_size);
                targetHeight = targetWidth - 20;
//                Log.e("MyRefreshHeader", "mIvEarth size  :" + targetWidth);
                float scale = ((float) targetHeight) / srcHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                targetBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth, srcHeight, matrix, true);
                targetBitmapWidth = targetBitmap.getWidth();
                //        Log.e("MyRefreshHeader", "targetBitmap size  :" + targetBitmapWidth + " , " +
                //          targetHeight);
            }
        });

    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mState = STATE_RESET;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mIvEarthTemp.setVisibility(View.INVISIBLE);
        mIvEarth.setVisibility(View.VISIBLE);
        mState = STATE_PREPARE;
    }


    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mState = STATE_BEGIN;
        this.hasBegin = true;
        mIvEarthTemp.setVisibility(View.VISIBLE);
        mIvEarth.setVisibility(View.GONE);
        mHandler.sendEmptyMessage(MSG_WHAT_UPDATE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mState = STATE_FINISH;
        //        mIvEearthBg.setVisibility(View.GONE);
        mHandler.removeMessages(MSG_WHAT_UPDATE);
        mIvEarthTemp.setVisibility(View.INVISIBLE);
        mIvEarth.setVisibility(this.hasBegin ? View.VISIBLE : View.INVISIBLE);
        curPosotion = 0;
        this.hasBegin = false;
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //处理提醒字体
        switch (mState) {
            case STATE_PREPARE:
                if (ptrIndicator.getCurrentPercent() <= 1) {
                    mIvEarth.setScaleX(ptrIndicator.getCurrentPercent());
                    mIvEarth.setScaleY(ptrIndicator.getCurrentPercent());
                }
                break;
            case STATE_BEGIN:

                break;
            case STATE_FINISH:
                if (ptrIndicator.getCurrentPercent() <= 1) {
                    mIvEarth.setScaleX(ptrIndicator.getCurrentPercent());
                    mIvEarth.setScaleY(ptrIndicator.getCurrentPercent());
                }
                break;
        }
    }

    public void setHeight(int height) {
        LayoutParams lp = (LayoutParams) mRootView.getLayoutParams();
        if (lp != null) {
            lp.height = height;
            mRootView.setLayoutParams(lp);
        }
    }
}
