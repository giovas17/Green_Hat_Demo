package com.giovani.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by darkgeat on 3/31/16.
 */
public class CustomScroll extends ScrollView {

    private boolean mScrollable = true;

    public CustomScroll(Context context) {
        super(context);
    }

    public CustomScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean ismScrollable() {
        return mScrollable;
    }

    public void setmScrollable(boolean mScrollable) {
        this.mScrollable = mScrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ismScrollable() && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mScrollable) return super.onTouchEvent(ev);
                return mScrollable;
            default:
                return super.onTouchEvent(ev);
        }
    }
}
