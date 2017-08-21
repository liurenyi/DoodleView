package com.lry.doodleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liurenyi on 2017/8/21.
 */

public class DoodleView extends View {

    private Paint mPaint;
    private Path mPath;
    private float mDownX, mDownY;
    private float mTempX, mTempY;
    private List<DrawPath> drawPathList;

    public DoodleView(Context context) {
        super(context);
        Log.d("liu","111");
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("liu","222");
        drawPathList = new ArrayList<>();
        initPaint();
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("liu","333");

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawPathList != null && !drawPathList.isEmpty()) {
            for (DrawPath drawPath : drawPathList) {
                if (drawPath.path != null) {
                    canvas.drawPath(drawPath.path,drawPath.paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mPath = new Path();
                mPath.moveTo(mDownX, mDownY);
                DrawPath drawPath = new DrawPath();
                drawPath.paint = mPaint;
                drawPath.path = mPath;
                drawPathList.add(drawPath);
                invalidate();
                mTempX = mDownX;
                mTempY = mDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                mPath.quadTo(mTempX, mTempY, moveX, moveY);
                invalidate();
                mTempX = moveX;
                mTempY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                initPaint();
                break;
        }
        return true;
    }

    public void undo() {
        if (drawPathList != null && drawPathList.size() >= 1) {
            drawPathList.remove(drawPathList.size() - 1);
            invalidate();
        }
    }
}
