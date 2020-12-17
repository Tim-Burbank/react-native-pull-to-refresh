package com.yunio.pulltorefresh.refresh.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yunio.pulltorefresh.refresh.header.DefaultRefreshHeader;

import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by JauZhou on 2018/3/23.
 */

public class DefaultRefreshView extends DPtrFrameLayout {

  public DefaultRefreshView(Context context) {
    super(context);
    initView();
  }

  public DefaultRefreshView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public DefaultRefreshView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initView();
  }

  private void initView() {
    DefaultRefreshHeader header = new DefaultRefreshHeader(getContext());
//    //      StoreHouseHeader header = new StoreHouseHeader(getContext());
    setHeaderView(header);
    addPtrUIHandler(header);
  }
}
