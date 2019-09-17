package com.sailflorve.guidelineview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;

import com.sailflorve.guidelineview.bean.Guideline;
import com.sailflorve.guidelineview.bean.shape.Circle;
import com.sailflorve.guidelineview.bean.shape.Rectangle;
import com.sailflorve.guidelineview.bean.shape.Shape;
import com.sailflorve.guidelineview.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class GuidelineView extends RelativeLayout {
    public interface GuidelineCallback {
        void onStart();

        void onShow(int index, Guideline guideline);

        void onFinish();
    }

    private static final String TAG = "GuidelineView";

    private Bitmap mBgBitmap;
    private Canvas mBgCanvas;
    private Paint mHighlightPaint; // 绘制高亮区域

    private int mBgColor; // 背景透明黑色

    private List<Guideline> mGuidelineList; // 储存每条Guideline

    private View mGuideView; // 显示功能引导文字的View

    private int mNextId; // 可点击进行下一步的View
    private OnClickListener mNextClickListener;

    private int mCurrentIndex; // 当前引导index

    private boolean isAdded;

    private GuidelineCallback mCallback;

    private GuidelineView(Context context) {
        super(context);
        init();
    }

    private GuidelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public static GuidelineView create(Context context) {
        return new GuidelineView(context);
    }

    private void init() {
        setWillNotDraw(false);

        mHighlightPaint = new Paint();
        mHighlightPaint.setColor(Color.BLACK);
        mHighlightPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mBgColor = Color.parseColor("#A1000000");

        mGuidelineList = new ArrayList<>();

        mCurrentIndex = -1;

        mNextClickListener = v -> next();

        //默认给自己设置点击下一步
        setOnClickListener(v -> {
            if (mGuidelineList.get(mCurrentIndex).getGuideArea().getView().findViewById(mNextId) == null) {
                next();
            }
        });

        mCallback = new GuidelineCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onShow(int index, Guideline guideline) {

            }

            @Override
            public void onFinish() {

            }
        };

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void drawHighlightOnBitmap() {
        mBgCanvas.drawPaint(mHighlightPaint);
        drawBackground(mBgCanvas);
        if (!mGuidelineList.isEmpty()) {
            if (mCurrentIndex >= mGuidelineList.size()) {
                return;
            }
            Guideline guideline = mGuidelineList.get(mCurrentIndex);
            drawHighlight(mBgCanvas, guideline.getHighlightArea().getShape());
            showGuideView(guideline);
            setBackground(new BitmapDrawable(getResources(), mBgBitmap));
        }
        invalidate();
    }

    /**
     * 添加一条引导
     */
    public GuidelineView add(Guideline guideline) {
        mGuidelineList.add(guideline);
        return this;
    }

    public GuidelineView setGuidelineCallback(GuidelineCallback callback) {
        mCallback = callback;
        return this;
    }

    /**
     * 开始显示功能引导
     */
    public void show(Activity activity) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        activity.addContentView(GuidelineView.this, params);

        post(this::showPost);
    }

    public GuidelineView setGuidelineBackgroundColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    private void showPost() {
        if (mBgBitmap == null) {
            mBgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mBgCanvas = new Canvas(mBgBitmap);
        }

        mCurrentIndex = -1;
        if (!isAdded) {
            initGuidelineList();
            isAdded = true;
        } else {
            setVisibility(VISIBLE);
        }

        mCallback.onStart();
        next();
    }


    /**
     * 隐藏功能引导
     */
    public void dismiss() {
        setVisibility(GONE);
        mGuidelineList.clear();
        mCurrentIndex = -1;
    }

    /**
     * 显示下一条功能引导
     */
    public void next() {
        mCurrentIndex++;
        if (mCurrentIndex >= mGuidelineList.size()) {
            dismiss();
            mCallback.onFinish();
        } else {
            drawHighlightOnBitmap();
            mCallback.onShow(mCurrentIndex, mGuidelineList.get(mCurrentIndex));
        }
    }

    /**
     * 取消自身的点击下一步，在{@link #initGuidelineList()}中设置给对应id的View
     *
     * @param id 可以点击下一步的View Id
     */
    public GuidelineView setNextButtonId(@IdRes int id) {
        mNextId = id;
        return this;
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(mBgColor);
    }

    private void initGuidelineList() {

        int[] locationOffset = new int[2];
        getLocationOnScreen(locationOffset);

        int locationOffsetY = locationOffset[1];
        Log.d(TAG, "initGuidelineList: " + locationOffsetY);

        for (Guideline guideline : mGuidelineList) {
            if (guideline.getHighlightArea().getShape() == null) {
                Shape shape = ViewUtil.getEncircleShape(
                        getContext(),
                        guideline.getHighlightArea().getView(),
                        guideline.getConfig().getShapeType(),
                        locationOffsetY
                );

                shape.scale(guideline.getConfig().getHighlightScale());
                guideline.getHighlightArea().setShape(shape);
            }
            if (guideline.getGuideArea().getView() == null) {
                View view = LayoutInflater.from(getContext()).inflate(
                        guideline.getGuideArea().getId(), null);
                Log.d(TAG, "initGuidelineList: " + view.getWidth());
                guideline.getGuideArea().setView(view);
            }
        }

        // 指定了点击的View，给其设置点击事件。
        if (mNextId != 0) {
            for (Guideline guideline : mGuidelineList) {
                View view = guideline.getGuideArea().getView().findViewById(mNextId);
                if (view != null) {
                    view.setOnClickListener(mNextClickListener);
                }
            }
        } else {
            setOnClickListener(mNextClickListener);
        }
    }

    /**
     * 绘制高亮区域
     */
    private void drawHighlight(Canvas canvas, Shape shape) {
        if (shape.getType() == Shape.CIRCLE) {
            Circle circle = (Circle) shape;
            canvas.drawCircle(circle.getCx(), circle.getCy(), circle.getRadius(),
                    mHighlightPaint);
        } else if (shape.getType() == Shape.RECT) {
            Rectangle rectangle = (Rectangle) shape;
            canvas.drawRoundRect(rectangle, 30, 30, mHighlightPaint);
        }
    }

    /**
     * 显示引导文字View
     */
    private void showGuideView(Guideline guideline) {
        if (mGuideView != null) {
            removeView(mGuideView);
        }

        mGuideView = guideline.getGuideArea().getView();

        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        addView(mGuideView, layoutParams);
        mGuideView.setVisibility(INVISIBLE);

        mGuideView.post(() -> {
            PointF cor = ViewUtil.getGuideCoordinate(
                    guideline.getHighlightArea().getShape(),
                    mGuideView,
                    guideline.getConfig().getDirection()
            );

            mGuideView.setX(cor.x + guideline.getConfig().getGuideOffsetX());
            mGuideView.setY(cor.y + guideline.getConfig().getGuideOffsetY());
            mGuideView.setScaleX(guideline.getConfig().getGuideScale());
            mGuideView.setScaleY(guideline.getConfig().getGuideScale());


            mGuideView.setVisibility(VISIBLE);
        });

    }

}
