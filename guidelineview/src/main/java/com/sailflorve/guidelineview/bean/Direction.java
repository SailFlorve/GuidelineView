package com.sailflorve.guidelineview.bean;

import androidx.annotation.IntDef;

/**
 * 复合角度使用&运算符连接：
 * 表示上方向：Direction.UP
 * 表示左上：Direction.UP & Direction.LEFT
 */
public class Direction {
    public static final int UP = 0xB;
    public static final int DOWN = 0x7;
    public static final int LEFT = 0xD;
    public static final int RIGHT = 0xE;

    @IntDef({UP, DOWN, LEFT, RIGHT})
    public @interface GuidelineDirection {
    }

    public static boolean is(int directions, @GuidelineDirection int angleConst) {
        return (directions | angleConst) == angleConst;
    }
}