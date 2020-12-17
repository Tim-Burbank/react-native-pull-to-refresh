package com.yunio.pulltorefresh.refresh.event;

/**
 * Created by JauZhou on 2018/3/23.
 */

public enum Events {

    ON_REFRESH("onRefresh");

    private final String mName;

    Events(final String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return mName;
    }
}
