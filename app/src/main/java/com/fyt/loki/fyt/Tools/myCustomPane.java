package com.fyt.loki.fyt.Tools;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ergas on 11/16/2017.
 */

public class myCustomPane extends SlidingPaneLayout {
    public myCustomPane(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public myCustomPane(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public myCustomPane(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    // ===========================================================
// Methods for/from SuperClass/Interfaces
// ===========================================================
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (this.isOpen()) {
            this.closePane();
        }
        return false; // here it returns false so that another event's listener
        // should be called, in your case the MapFragment
        // listener
    }
}
