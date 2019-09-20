# GuidelineView
[![](https://jitpack.io/v/SailFlorve/GuidelineView.svg)](https://jitpack.io/#SailFlorve/GuidelineView)  
 
Android显示功能引导的自定义View。可以自动高亮View区域，也可以指定高亮区域；引导内容显示支持layout和View对象方式，并指定view设置点击事件；支持设置引导内容相对高亮区域的显示方向；可以直接在onCreate()中调用。

## 图片示例
![sample](https://github.com/SailFlorve/GuidelineView/blob/master/sample.gif?raw=true)
## 导入
在项目的build.gradle中添加JitPack仓库：<br>
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
在模块的build.gradle中导入：
```
dependencies {
	implementation 'com.github.SailFlorve:GuidelineView:1.0'
}
``` 

## 使用方法
#### 选择引导内容的显示方式
选择直接创建View  
```
TextView guideText = new TextView(this);
guideText.setText("Hello!");
guideText.setTextColor(Color.WHITE);
``` 

选择使用布局文件作为引导内容
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="123123"
        android:textColor="@android:color/white"
        android:textSize="25sp" />

    <ImageView
        android:id="@+id/iv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@android:mipmap/sym_def_app_icon" />

</LinearLayout>
```
#### 创建Guideline
选择需要引导的View
```
private Toolbar mToolbar;
private FloatingActionButton mFab;
private TextView mTextView;
```
使用Guideline.builder().build()创建Guideline对象
```
Guideline fabGl = new Guideline.Builder()
        .setHighlight(mFab) //设置被高亮的区域
        .setGuideView(guideText) //引导内容
        .setDirection(Direction.LEFT) //引导内容所在的方向为左方
        .setGuideOffsetX(-ViewUtil.dip2px(this, 15)) //引导内容在X轴偏移
        .setGuideScale(1.5f) //引导内容的缩放
        .build();

Guideline textGl = new Guideline.Builder()
        .setHighlight(mTextView)
        .setGuideView(R.layout.guide)
        .setDirection(Direction.UP & Direction.RIGHT) //引导内容所在的方向为右上方
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
        
<<<<<<< HEAD
Guideline customGl = new Guideline.Builder()
        .setHighlight(new Circle(600, 1200, 200))
=======
Guideline customGl = Guideline.builder()
        .setHighlight(new Circle(600, 1200, 200)) //直接创建一个圆作为高亮区域
>>>>>>> origin/master
        .setGuideView(R.layout.guide)
        .setDirection(Direction.LEFT & Direction.UP)
        .build();
```

#### 创建GuidelineView，设置回调，添加Guideline，并开始显示引导内容
```
GuidelineView.create(this)
	//Guideline按添加顺序显示
        .add(fabGl)
	.add(textGl)
	.add(toolbarGl)
	.add(customGl)
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
```

## 类说明

#### Guideline.Builder
| 方法名                    | 说明                 | 返回类型| 
| ---------------------------- | ---------------------- | ---------------------- |
| setHighlight(Shape)<br>setHighlight(View) | 使用Shape类直接指定高亮区域，或指定被高亮的View，若均被设置则选择Shape | GuideArea.Builder |
| setGuideView(@LayoutRes int) <br> setGuideView(View) | 使用View或layout作为引导内容，若均被设置则选择View | GuideConfig.Builder |
| setDirection(int)      | 引导内容相对于高亮区域的方向 | GuideConfig.Builder |
| setGuideOffsetX(float) | 引导内容在x轴的偏移量 |GuideConfig.Builder |
| setGuideOffsetY(float) | 引导内容在y轴的偏移量 |GuideConfig.Builder |
| setGuideScale(float)     | 引导内容的缩放        |GuideConfig.Builder |
| setHighlightScale(float) | 高亮区域的缩放        |GuideConfig.Builder |
| setShapeType(int)        | 高亮形状(圆角矩形或者圆形) |GuideConfig.Builder |
| build()        | 创建Guideline对象 |Guideline |


#### Guideline
| 方法名 | 说明 |
| --------- | ----------------------- |
| setHighlightArea(Guideline.HighlightArea) | 设置高亮区域 |
| setGuideArea(Guideline.GuideArea) | 设置提示文字 |
| setConfig(Guideline.Config) | 设置Config |
| getters... |                                   |



#### Guideline.HighlightArea
| 方法名 | 说明 |
| --------- | ----------------------- |
| setView(View) | 设置高亮区域为一个View |
| setShape(Shape) | 使用Shape类指定高亮区域的坐标 |

#### Guideline.GuideArea
| 方法名 | 说明 |
| --------- | ----------------------- |
| setView(View) | 设置提示内容为一个View |
| setShape(@layoutRes int) | 设置提示内容为一个layout |


#### Guideline.Config
| 方法名 | 说明 |
| --------- | ----------------------- |
| setDirection(int)      | 引导内容相对于高亮区域的方向 |
| setGuideOffsetX(float) | 引导内容在x轴的偏移量 |
| setGuideOffsetY(float) | 引导内容在y轴的偏移量 |
| setGuideScale(float)     | 引导内容的缩放        |
| setHighlightScale(float) | 高亮区域的缩放        |
| setShapeType(int)        | 高亮形状(圆角矩形或者圆形) |


#### GuidelineView
| 方法名                                  | 说明                                                                |
| ------------------------------------------ | --------------------------------------------------------------------- |
| create(Context)                            | 获得一个GuidelineView实例                                       |
| add(Guideline)                             | 添加一条引导                                                    |
| setGuidelineCallback(GuidelineCallback)    | 设置引导显示的回调                                           |
| setGuidelineBackgroundColor(@ColorInt int) | 设置背景变暗的颜色                                           |
| show(Activity)                             | 显示引导                                                          |
| next()                                     | 显示下一条引导内容                                           |
| dismiss()                                  | 取消显示引导                                                    |
| setNextButtonId(@IdRes int)                | 设置点击按钮进行下一条引导的View id，如果不设置，点击任意处显示下一条 |


#### Shape及其实现类
Shape类是高亮区域的实体类。<br>

| 方法/属性 | 说明 |
| ------------- | ---------------- |
| CIRCLE, RECT | 表示形状类型(圆形，圆角矩形) |
| scale(float)  | 将该形状缩放 |
| getType()     | 获取形状类型 |
| getCenter()   | 获取形状的中心点 |
| getWidth()    | 获取形状宽度 |
| getHeight()   | 获取形状高度 |

| 方法                      | 说明                           |
| --------------------------- | -------------------------------- |
| Circle(float, float, float) | 传入圆心坐标和半径，创建一个圆形 |

| 方法                                | 说明                                     |
| ------------------------------------- | ------------------------------------------ |
| Rectangle(float, float, float, float) | 传入四个边距离边界的长度，创建一个圆角矩形 |

#### Direction
| 方法/属性 | 说明           |
| ------------- | ---------------- |
| UP, DOWN, LEFT, RIGHT | 表示方向，使用&复合方向 |
