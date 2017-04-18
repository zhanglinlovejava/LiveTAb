package com.colin.livetvtab.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.colin.livetvtab.R;

public class ColorTrackView extends View {

	private static final int DIRECTION_LEFT = 0;
	private static final int DIRECTION_RIGHT = 1;
	private int mDirection = DIRECTION_LEFT;

	private int mTextStartX;

	private Paint mPaint;

	private int mTextOriginColor = 0xffffff;
	private int mTextChangeColor = 0xff0000;

	private String mText = "xx";
	private int mTextSize = DisplayUtil.dip2px(getContext(), 30);
	private int mTextWidth;

	private int mRealWidth;

	private float mProgress;

	private Rect mTextBound = new Rect();

	public ColorTrackView(Context context) {
		super(context, null);
	}

	public ColorTrackView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ColorTrackView);
		mText = ta.getString(R.styleable.ColorTrackView_text);
		mTextSize = ta.getDimensionPixelSize(
				R.styleable.ColorTrackView_textSize, mTextSize);
		mTextOriginColor = ta.getColor(
				R.styleable.ColorTrackView_textOriginColor,
				mTextOriginColor);
		mTextChangeColor = ta.getColor(
				R.styleable.ColorTrackView_textChangeColor,
				mTextChangeColor);
		mProgress = ta.getFloat(R.styleable.ColorTrackView_progress, 0);
		mDirection = ta.getInt(R.styleable.ColorTrackView_direction,
				mDirection);

		ta.recycle();
		mPaint.setTextSize(mTextSize);

	}

	private void measureText() {
		mTextWidth = (int) mPaint.measureText(mText);
		mPaint.getTextBounds(mText, 0,mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
		measureText();
		mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		mTextStartX = mRealWidth / 2 - mTextWidth / 2;

	}

	private int measureHeight(int measureSpec) {
		int mode = MeasureSpec.getMode(measureSpec);
		int val = MeasureSpec.getSize(measureSpec);
		int result = 0;
		switch (mode) {
			case MeasureSpec.EXACTLY:
				result = val;
				break;
			case MeasureSpec.AT_MOST:
			case MeasureSpec.UNSPECIFIED:
				result = mTextBound.height();
				break;
		}
		result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
		return result + getPaddingTop() + getPaddingBottom();
	}

	private int measureWidth(int measureSpec) {
		int mode = MeasureSpec.getMode(measureSpec);
		int val = MeasureSpec.getSize(measureSpec);
		int result = 0;
		switch (mode) {
			case MeasureSpec.EXACTLY:
				result = val;
				break;
			case MeasureSpec.AT_MOST:
			case MeasureSpec.UNSPECIFIED:
				result = mTextBound.width();
				break;
		}
		result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
		return result + getPaddingLeft() + getPaddingRight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mDirection == DIRECTION_LEFT) {
			drawChangeLeft(canvas);
			drawOriginLeft(canvas);
		} else if (mDirection == DIRECTION_RIGHT) {
			drawOriginRight(canvas);
			drawChangeRight(canvas);
		} else {
			System.out.println("ColorTrackView direction error");
		}

	}

	private void drawChangeRight(Canvas canvas) {
		drawText(canvas, mTextChangeColor, (int) (mTextStartX + (1 - mProgress)
				* mTextWidth), mTextStartX + mTextWidth);
	}

	private void drawOriginRight(Canvas canvas) {
		drawText(canvas, mTextOriginColor, mTextStartX,
				(int) (mTextStartX + (1 - mProgress) * mTextWidth));
	}

	private void drawChangeLeft(Canvas canvas) {
		drawText(canvas, mTextChangeColor, mTextStartX,
				(int) (mTextStartX + mProgress * mTextWidth));
	}

	private void drawOriginLeft(Canvas canvas) {
		drawText(canvas, mTextOriginColor, (int) (mTextStartX + mProgress
				* mTextWidth), mTextStartX + mTextWidth);
	}

	private void drawText(Canvas canvas, int color, int startX, int endX) {
		mPaint.setColor(color);
		canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.clipRect(startX, 0, endX, getMeasuredHeight());
		canvas.drawText((String) mText, mTextStartX, getMeasuredHeight()
				/ 2 + mTextBound.height() / 2, mPaint);
		canvas.restore();
	}

	public int getDirection() {
		return mDirection;
	}

	public void setDirection(int mDirection) {
		this.mDirection = mDirection;
		invalidate();
	}

	public int getTextOriginColor() {
		return mTextOriginColor;
	}

	public void setTextOriginColor(int mTextOriginColor) {
		this.mTextOriginColor = mTextOriginColor;
		invalidate();
	}

	public int getTextChangeColor() {
		return mTextChangeColor;
	}

	public void setTextChangeColor(int mTextChangeColor) {
		this.mTextChangeColor = mTextChangeColor;
		invalidate();
	}

	public String getText() {
		return mText;
	}

	public void setText(String mText) {
		this.mText = mText;
	}

	public int getTextSize() {
		return mTextSize;
	}

	public void setTextSize(int mTextSize) {
		this.mTextSize = mTextSize;
		requestLayout();
		invalidate();
	}

	public float getProgress() {
		return mProgress;
	}

	public void setProgress(float mProgress) {
		this.mProgress = mProgress;
		invalidate();
	}

}
