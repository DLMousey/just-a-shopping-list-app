package com.enderstudy.shoppinglist.listeners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class CheckboxItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    public interface OnRecyclerClickListener {
        void OnItemClick(View view, int position);
    }

    private final OnRecyclerClickListener clickListener;
    private final GestureDetector gestureDetector;

    public CheckboxItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        clickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && clickListener != null) {
                    clickListener.OnItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }

                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        if(this.gestureDetector != null) {
            return this.gestureDetector.onTouchEvent(e);
        } else {
            return false;
        }
    }
}
