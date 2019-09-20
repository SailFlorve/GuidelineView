package com.sailflorve.guidelineviewsample;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sailflorve.guidelineview.bean.Direction;
import com.sailflorve.guidelineview.bean.Guideline;
import com.sailflorve.guidelineview.bean.shape.Circle;
import com.sailflorve.guidelineview.bean.shape.Shape;
import com.sailflorve.guidelineview.util.ViewUtil;
import com.sailflorve.guidelineview.view.GuidelineView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTextView = findViewById(R.id.tv);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        showGuideline();
    }

    private void showGuideline() {
        //创建一个TextView作为引导内容
        TextView guideText = new TextView(this);
        guideText.setText("Hello!");
        guideText.setTextColor(Color.WHITE);

        Guideline fabGl = new Guideline.Builder()
                .setHighlight(mFab) //设置被高亮的区域
                .setGuideView(guideText) //引导内容
                .setDirection(Direction.LEFT) //引导内容所在的方向：左
                .setGuideOffsetX(-ViewUtil.dip2px(this, 15)) //引导内容在X轴的偏移
                .setGuideScale(1.5f) //引导内容的缩放
                .build();

        Guideline textGl = new Guideline.Builder()
                .setHighlight(mTextView)
                .setGuideView(R.layout.guide)
                .setDirection(Direction.UP & Direction.RIGHT) //引导内容所在的方向：右上
                .setShapeType(Shape.RECT) //高亮区域的形状
                .setHighlightScale(1.2f) //高亮区域的缩放
                .build();

        Guideline toolbarGl = new Guideline.Builder()
                .setHighlight(mToolbar)
                .setGuideView(R.layout.guide)
                .setDirection(Direction.DOWN)
                .setShapeType(Shape.RECT)
                .setHighlightScale(0.95f)
                .build();

        Guideline customGl = new Guideline.Builder()
                .setHighlight(new Circle(600, 1200, 200))
                .setGuideView(R.layout.guide)
                .setDirection(Direction.LEFT & Direction.UP)
                .build();

        GuidelineView.create(this)
                .add(fabGl).add(textGl).add(toolbarGl).add(customGl)
                .setNextButtonId(R.id.iv) //设置点击该View才显示下一个
                //引导显示回调
                .setGuidelineCallback(new GuidelineView.GuidelineCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onShow(int index, Guideline guideline) {
                        Toast.makeText(MainActivity.this, "Guide " + index, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(MainActivity.this, "Guide finish", Toast.LENGTH_SHORT).show();
                    }
                }).show(this);
    }
}
