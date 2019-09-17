package com.sailflorve.guidelineview.bean;

import android.view.View;

import androidx.annotation.LayoutRes;

import com.sailflorve.guidelineview.bean.shape.Shape;


/**
 * 功能引导实体类
 */
public class Guideline {

    private HighlightArea highlightArea;
    private GuideArea guideArea;
    private Config config;

    private Guideline() {
    }

    public static HighlightArea.Builder builder() {
        Guideline guideline = new Guideline();
        return new HighlightArea.Builder(guideline);
    }

    public HighlightArea getHighlightArea() {
        return highlightArea;
    }

    public GuideArea getGuideArea() {
        return guideArea;
    }

    public Config getConfig() {
        return config;
    }

    public void setHighlightArea(HighlightArea highlightArea) {
        this.highlightArea = highlightArea;
    }

    public void setGuideArea(GuideArea guideArea) {
        this.guideArea = guideArea;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class GuideArea {

        private int mId;
        private View mView;

        private GuideArea() {
        }

        public int getId() {
            return mId;
        }

        public View getView() {
            return mView;
        }

        public void setView(View view) {
            this.mView = view;
        }

        public void setId(int mId) {
            this.mId = mId;
        }

        public static class Builder {
            GuideArea area;
            Guideline guideline;

            Builder(Guideline guideline) {
                area = new GuideArea();
                this.guideline = guideline;
            }

            public Config.Builder setGuideView(@LayoutRes int id) {
                area.mId = id;
                guideline.setGuideArea(area);
                return new Config.Builder(guideline);

            }

            public Config.Builder setGuideView(View view) {
                area.mView = view;
                guideline.setGuideArea(area);
                return new Config.Builder(guideline);
            }
        }
    }

    public static class Config {
        private int direction = Direction.UP;
        private float guideOffsetX = 0;
        private float guideOffsetY = 0;
        private float guideScale = 1;
        private float highlightScale = 1;

        private int shapeType;

        public int getDirection() {
            return direction;
        }

        public float getGuideOffsetX() {
            return guideOffsetX;
        }

        public float getGuideOffsetY() {
            return guideOffsetY;
        }

        public float getGuideScale() {
            return guideScale;
        }

        public float getHighlightScale() {
            return highlightScale;
        }

        public int getShapeType() {
            return shapeType;
        }

        public Config setDirection(int direction) {
            this.direction = direction;
            return this;
        }

        public Config setGuideOffsetX(float guideOffsetX) {
            this.guideOffsetX = guideOffsetX;
            return this;
        }

        public Config setGuideOffsetY(float guideOffsetY) {
            this.guideOffsetY = guideOffsetY;
            return this;
        }

        public Config setGuideScale(float guideScale) {
            this.guideScale = guideScale;
            return this;
        }

        public Config setHighlightScale(float highlightScale) {
            this.highlightScale = highlightScale;
            return this;
        }

        public Config setShapeType(int shapeType) {
            this.shapeType = shapeType;
            return this;
        }

        public static class Builder {
            Config config;
            Guideline guideline;

            Builder(Guideline guideline) {
                config = new Config();
                this.guideline = guideline;
            }

            public Guideline build() {
                guideline.setConfig(config);
                return guideline;
            }

            public Builder setConfig(Config config) {
                this.config = config;
                return this;
            }

            public Builder setDirection(int d) {
                config.direction = d;
                return this;
            }

            public Builder setGuideOffsetX(float guideOffsetX) {
                config.guideOffsetX = guideOffsetX;
                return this;
            }

            public Builder setGuideOffsetY(float guideOffsetY) {
                config.guideOffsetY = guideOffsetY;
                return this;
            }

            public Builder setGuideScale(float guideScale) {
                config.guideScale = guideScale;
                return this;
            }

            public Builder setHighlightScale(float highlightScale) {
                config.highlightScale = highlightScale;
                return this;
            }

            public Builder setShapeType(int shapeType) {
                config.shapeType = shapeType;
                return this;
            }
        }
    }

    public static class HighlightArea {

        private Shape mShape;
        private View mView;

        private HighlightArea() {
        }

        public Shape getShape() {
            return mShape;
        }

        public void setShape(Shape shape) {
            mShape = shape;
        }

        public View getView() {
            return mView;
        }

        public void setView(View view) {
            this.mView = view;
        }

        public static class Builder {
            HighlightArea area;
            Guideline guideline;

            Builder(Guideline guideline) {
                area = new HighlightArea();
                this.guideline = guideline;
            }

            public GuideArea.Builder setHighlight(Shape shape) {
                area.mShape = shape;
                guideline.setHighlightArea(area);
                return new GuideArea.Builder(guideline);
            }

            public GuideArea.Builder setHighlight(View view) {
                area.mView = view;
                guideline.setHighlightArea(area);
                return new GuideArea.Builder(guideline);
            }

        }

    }
}
