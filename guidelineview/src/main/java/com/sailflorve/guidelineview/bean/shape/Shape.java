package com.sailflorve.guidelineview.bean.shape;

import android.graphics.PointF;

import androidx.annotation.IntDef;

/**
 * 高亮区域形状的实体类，保存了形状的位置、大小等
 */
public interface Shape {
    int CIRCLE = 0;
    int RECT = 1;

    @IntDef({CIRCLE, RECT})
    public @interface ShapeType {

    }

    void scale(float ratio);

    int getType();

    PointF getCenter();

    float getWidth();

    float getHeight();
}
