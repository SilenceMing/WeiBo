package com.xiaoming.cweibo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * 自定义一个背景图片可以居中的textView
 */
public class DrawCenterTextView extends android.support.v7.widget.AppCompatTextView {
    public DrawCenterTextView(Context context) {
        super(context);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    protected void onDraw(Canvas canvas) {
        //获取textView的上下左右的drawable数组
        Drawable drawables[] = getCompoundDrawables();
        //获取左边的draw
        Drawable drawableLeft = drawables[0];
        if (null != drawableLeft) {
            setGravity(Gravity.START);
            //获取draw的宽度
            int drawWidth = drawableLeft.getIntrinsicWidth();
            //因为设置padding，所以重新进行计算
            int drawablePadding= getCompoundDrawablePadding();
            //获取文本的高度
            int textWidth = (int) getPaint().measureText(getText().toString());
            int bodyWidth = drawWidth + textWidth+drawablePadding;
            //让内容进行偏移
            canvas.translate((getWidth() - bodyWidth)/ 2, 0);
        }
        Drawable drawableRight = drawables[2];
        if (null != drawableRight) {
            setGravity(Gravity.END);
            int drawWidth = drawableRight.getIntrinsicWidth();
            int drawablePadding= getCompoundDrawablePadding();
            int textWidth = (int) getPaint().measureText(getText().toString());
            int bodyWidth = drawWidth + textWidth+drawablePadding;
            canvas.translate(-(getWidth() - bodyWidth)/ 2, 0);
        }
        super.onDraw(canvas);
    }
}
