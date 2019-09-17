package com.sailflorve.guidelineview.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

import com.sailflorve.guidelineview.bean.Direction;
import com.sailflorve.guidelineview.bean.shape.Circle;
import com.sailflorve.guidelineview.bean.shape.Rectangle;
import com.sailflorve.guidelineview.bean.shape.Shape;


public class ViewUtil {
    private static final String TAG = "ViewUtil";

    /**
     * 根据View，得到可以包围住它的形状
     *
     * @param view 需要高亮的View
     * @param type 形状类型
     */
    public static Shape getEncircleShape(Context context, View view, int type, int offset) {
        if (type == Shape.CIRCLE) {
            return getWrappedCircle(context, view, offset);
        } else {
            return getWrappedRect(context, view, offset);
        }
    }

    private static Rectangle getWrappedRect(Context context, View view, int offset) {
        int[] location = getLocationInActivity(context, view);
        Rectangle rectangle = new Rectangle();
        rectangle.left = location[0];
        rectangle.top = location[1] - offset;
        rectangle.right = location[0] + view.getWidth();
        rectangle.bottom = location[1] + view.getHeight() - offset;
        return rectangle;
    }

    private static Circle getWrappedCircle(Context context, View view, int offset) {
        int[] location = getLocationInActivity(context, view);
        float centerX = location[0] + view.getWidth() / 2f;
        float centerY = location[1] + view.getHeight() / 2f;
        float radius = getDistance(centerX, centerY, location[0], location[1]);
        return new Circle(centerX, centerY - offset, radius);
    }

    /**
     * 得到view在activity中的绝对坐标
     */
    private static int[] getLocationInActivity(Context context, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    private static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private static float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow((y2 - y1), 2));
    }

    /**
     * 得到引导View的坐标
     *
     * @param shape 高亮区域
     * @param view  引导View
     * @param angle 引导所在的角度
     */
    public static PointF getGuideCoordinate(Shape shape, View view, int angle) {
        Log.d(TAG, "getGuideCoordinate: setHighlightShape" + shape.getCenter());
        Log.d(TAG, "getGuideCoordinate: setHighlightShape height width" + shape.getHeight() + shape.getWidth());
        Log.d(TAG, "getGuideCoordinate: guideview" + view.getWidth() + view.getHeight());

        float centerX = shape.getCenter().x;
        float centerY = shape.getCenter().y;

        if (Direction.is(angle, Direction.UP)) {
            Log.d(TAG, "getGuideCoordinate: up");
            centerY -= (shape.getHeight() + view.getHeight()) / 2f;
        } else if (Direction.is(angle, Direction.DOWN)) {
            Log.d(TAG, "getGuideCoordinate: down");
            centerY += (shape.getHeight() + view.getHeight()) / 2f;
        }
        if (Direction.is(angle, Direction.LEFT)) {
            Log.d(TAG, "getGuideCoordinate: left");
            centerX -= shape.getWidth() / 2f + view.getWidth() / 2.5f;
        } else if (Direction.is(angle, Direction.RIGHT)) {
            Log.d(TAG, "getGuideCoordinate: right");
            centerX += shape.getWidth() / 2f + view.getWidth() / 2.5f;
        }

        float x = centerX - view.getWidth() / 2f;
        float y = centerY - view.getHeight() / 2f;

        Log.d(TAG, "getGuideCoordinate: " + x + " " + y);
        return new PointF(x, y);
    }

    public static int dip2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
