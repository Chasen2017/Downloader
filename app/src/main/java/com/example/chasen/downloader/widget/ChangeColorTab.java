package com.example.chasen.downloader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.chasen.downloader.R;

/**
 * 仿微信变色Tab实现
 */

public class ChangeColorTab extends View {

    // onSaveInstanceState中的标识符，用做key
    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    // 图标
    private Bitmap mIconBitmap;
    // 颜色
    private int mColor = 0xFF45c01A;  // 绿色
    // 文字
    private String mText = "正在下载";
    // 文字大小
    private int mTextSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            12,
            getResources().getDisplayMetrics());


    private Canvas mCanvas;
    private Paint mPaint;
    private Bitmap mBitmap;

    private float mAlpha;
    private Rect mIconRect;
    private Rect mTextBound;

    private Paint mTextPaint;

    public ChangeColorTab(Context context) {
        this(context, null);
    }

    public ChangeColorTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // 初始化icon text
    private void init(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorTab);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.ChangeColorTab_icon:
                    BitmapDrawable drawable = (BitmapDrawable) ta.getDrawable(index);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorTab_color:
                    mColor = ta.getColor(index, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorTab_text:
                    mText = ta.getString(index);
                    break;
                case R.styleable.ChangeColorTab_textSize:
                    mTextSize = (int) ta.getDimension(index,
                            TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_SP,
                                    12,
                                    getResources().getDisplayMetrics()));
                    break;
            }
        }
        ta.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xFF555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (iconWidth + mTextBound.height()) / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        int alpha = (int) Math.ceil(255 * mAlpha);
        // 准备mBitmap, setAlpha, 纯色, xfermode, 图标
        setupTargetBitmap(alpha);
        // 绘制原文本
        drawSourceText(canvas, alpha);
        // 绘制变色的文本
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    // 绘制可变色的Icon
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    // 绘制原文本
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xFF333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    // 绘制变色的文本
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }


    // 设置透明度
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    // 重绘
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }


    // 发送意外情况后数据的保存，横竖屏的切换等
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
