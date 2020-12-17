package com.yunio.pulltorefresh.refresh.view;


import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by JauZhou on 2018/3/23.
 */

public class DPtrFrameLayout extends PtrFrameLayout {

    private boolean isFinishInflate = false;

    public DPtrFrameLayout(Context context) {
        super(context);
    }

    public DPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isFinishInflate) {
            onFinishInflate();
            isFinishInflate = true;
        }
    }
}
