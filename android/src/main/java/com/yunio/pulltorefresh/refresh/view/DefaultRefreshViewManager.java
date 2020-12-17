package com.yunio.pulltorefresh.refresh.view;

import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.yunio.pulltorefresh.refresh.event.Events;
import com.yunio.pulltorefresh.refresh.header.DefaultRefreshHeader;

import java.util.Map;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by JauZhou on 2018/3/21.
 */

public class DefaultRefreshViewManager extends ViewGroupManager<DefaultRefreshView> {

  private final static String REACT_CLASS = "RTCDefaultRefreshViewManager";

  @Override
  public String getName() {
    return REACT_CLASS;
  }


  @Override
  @Nullable
  public Map getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder builder = MapBuilder.builder();
    for (Events event : Events.values()) {
      builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
    }
    return builder.build();
  }

  @Override
  protected DefaultRefreshView createViewInstance(ThemedReactContext reactContext) {
    //        final PtrFrameLayout ptrFrameLayout = new PtrFrameLayout(reactContext);
    //        final RCTEventEmitter mEventEmitter = reactContext.getJSModule(RCTEventEmitter.class);
    //        DefaultRefreshHeader headerView = new DefaultRefreshHeader(reactContext) {
    //            @Override
    //            public void onUIRefreshBegin(PtrFrameLayout frame) {
    //                super.onUIRefreshBegin(frame);
    //                mEventEmitter.receiveEvent(ptrFrameLayout.getId(), Events.ON_REFRESH.toString(), Arguments
    // .createMap());
    //            }
    //        };
    //        ptrFrameLayout.setHeaderView(headerView);
    //        ptrFrameLayout.addPtrUIHandler(headerView);
    //        return new DefaultRefreshView(reactContext);
    return new DefaultRefreshView(reactContext);
  }

  //    @ReactProp(name = "isContentScroll")
  //    public void setContentScroll(PtrFrameLayout root, boolean isContentScroll) {
  //        root.setPinContent(!isContentScroll);
  //    }
  //
  //    @ReactProp(name = "refreshable")
  //    public void setRefreshable(PtrFrameLayout root, boolean refreshable) {
  //        root.setEnabled(refreshable);
  //    }


  @Override
  protected void addEventEmitters(final ThemedReactContext reactContext, DefaultRefreshView view) {
    super.addEventEmitters(reactContext, view);
    view.setPtrHandler(new PtrDefaultHandler() {
      @Override
      public void onRefreshBegin(PtrFrameLayout frame) {
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(frame.getId(), Events.ON_REFRESH
          .toString(), Arguments.createMap());
      }
    });
    //    view.setOnRefreshListener(new OnRefreshListener() {
    //      @Override
    //      public void onRefresh(RefreshLayout refreshlayout) {
    //        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(refreshlayout.getLayout().getId(), Events
    //          .ON_REFRESH.toString(), Arguments.createMap());
    //      }
    //    });
  }

  @ReactProp(name = "refreshing")
  public void setRefreshing(final DefaultRefreshView root, final boolean refreshing) {
    root.post(new Runnable() {
      @Override
      public void run() {
        if (refreshing) {
          root.autoRefresh();
        } else {
          root.refreshComplete();
          //          root.finishRefresh();
        }
      }
    });
  }

  @ReactProp(name = "height")
  public void setHeight(DefaultRefreshView root, int height) {
    View headerView = root.getHeaderView();
    if (headerView instanceof DefaultRefreshHeader) {
      DefaultRefreshHeader refreshHeader = (DefaultRefreshHeader) headerView;
      refreshHeader.setHeight(height);
    }
  }
}
