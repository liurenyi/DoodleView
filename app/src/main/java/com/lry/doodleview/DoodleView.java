package com.lry.doodleview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.preference.PreferenceManager;
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
    private List<DrawPath> cacheDrawPathList;
    public DoodleView(Context context) {
        super(context);
        Log.d("liu", "111");
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("liu", "222");
        drawPathList = new ArrayList<>();
        cacheDrawPathList = new ArrayList<>();
        initPaint();
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("liu", "333");

    }

    private void initPaint() {
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        int color = share.getInt("colorType", 0);
        float paintWidth = share.getFloat("paintWidth", 0);
        mPaint = new Paint();

        if (color != 0) {
            mPaint.setColor(color);
        }

        if (paintWidth != 0) {
            mPaint.setStrokeWidth(paintWidth);
        } else {
            mPaint.setStrokeWidth(5);
        }

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawPathList != null && !drawPathList.isEmpty()) {
            for (DrawPath drawPath : drawPathList) {
                if (drawPath.path != null) {
                    canvas.drawPath(drawPath.path, drawPath.paint);
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
            cacheDrawPathList.add(drawPathList.get(drawPathList.size() - 1)); // 先把要移除的笔画保存起来
            drawPathList.remove(drawPathList.size() - 1);
            invalidate();
        }
    }

    public void backUndo() {
        if (cacheDrawPathList != null && cacheDrawPathList.size() >= 1) {
            drawPathList.add(cacheDrawPathList.get(cacheDrawPathList.size() - 1)); // 先把要增加的笔画保存起来
            cacheDrawPathList.remove(cacheDrawPathList.size() - 1);
            invalidate();
        }
    }

    /**
     * 添加改变画笔颜色方法
     * @param color
     */
    public void resetColor(int color) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putInt("colorType",color);
        editor.apply(); // 保存当前画笔颜色状态
        mPaint.setColor(color);
    }

    /**
     * 改变画笔宽度
     * @param paintWidth
     */
    public void resetPaintWidth(float paintWidth) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putFloat("paintWidth",paintWidth);
        editor.apply(); // 保存当前画笔宽度
        mPaint.setStrokeWidth(paintWidth);
    }
}
