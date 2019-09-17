package com.sailflorve.guidelineview.bean.shape;

import android.graphics.PointF;

public class Circle implements Shape {
    private float cx;
    private float cy;
    private float radius;

    public Circle() {

    }

    public Circle(float cx, float cy, float radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void scale(float ratio) {
        radius = radius * ratio;
    }

    @Override
    public int getType() {
        return Shape.CIRCLE;
    }

    @Override
    public PointF getCenter() {
        return new PointF(cx, cy);
    }

    @Override
    public float getWidth() {
        return radius * 2;
    }

    @Override
    public float getHeight() {
        return radius * 2;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "cx=" + cx +
                ", cy=" + cy +
                ", radius=" + radius +
                '}';
    }
}