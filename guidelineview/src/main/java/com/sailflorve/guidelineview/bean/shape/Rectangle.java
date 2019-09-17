package com.sailflorve.guidelineview.bean.shape;

import android.graphics.PointF;
import android.graphics.RectF;

public class Rectangle extends RectF implements Shape {
    public Rectangle() {
    }

    public Rectangle(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public int getType() {
        return Shape.RECT;
    }

    @Override
    public PointF getCenter() {
        return new PointF(centerX(), centerY());
    }

    @Override
    public float getWidth() {
        return width();
    }

    @Override
    public float getHeight() {
        return height();
    }

    @Override
    public void scale(float ratio) {
        float offset = (ratio - 1) / 2f;

        left -= offset * getWidth();
        right += offset * getWidth();
        top -= offset * getHeight();
        bottom += offset * getHeight();
    }
}
