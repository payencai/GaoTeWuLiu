package com.gaote.wuliu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.gaote.wuliu.tools.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyStepView extends View {
    private int radius=DensityUtil.dip2px(getContext(),10);;//圆半径
    private int textHeight;//文字高度
    private int height=0;//view的高度
    private int width=0;//view的宽度
    private int stepSize=3;//有多少个步骤
    private Paint mCirclePaint;//圆形画笔，线条画笔
    private Paint mBluePaint;//下标文字蓝色画笔
    private Paint mWhitePaint;//圆圈内文字白色画笔
    private Paint mLinePaint;//圆圈内线条画笔
    private int currentStep=1;//当前步骤
    private List<String> mSteps = new ArrayList<>();//每一步的文字信息


    public MyStepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intPaint();
        initData();
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
        invalidate();
    }

    private void initData() {
        String[] steps = {"Step1", "Step2", "Step3"};
        setSteps(Arrays.asList(steps));

    }
    public void setSteps(List<String> steps) {
        mSteps.clear();
        if (steps != null) {
            mSteps.addAll(steps);
        }
        currentStep=1;
        invalidate();//重新绘制
    }
    //初始化画笔
    private void intPaint(){

        //初始化圆圈画笔
        mCirclePaint=new Paint();
        mCirclePaint.setTextAlign(Paint.Align.CENTER);
        mCirclePaint.setStrokeWidth(0);
        mCirclePaint.setColor(Color.parseColor("#00C1DE"));
        mCirclePaint.setStyle(Paint.Style.FILL);
        //初始化蓝色文字画笔
        mBluePaint=new Paint();
        mBluePaint.setTextAlign(Paint.Align.CENTER);
        mBluePaint.setColor(Color.parseColor("#00C1DE"));
        mBluePaint.setTextSize(DensityUtil.dip2px(getContext(),12));
        ///初始化白色文字画笔
        mWhitePaint=new Paint();
        mWhitePaint.setColor(Color.WHITE);
        mWhitePaint.setTextAlign(Paint.Align.CENTER);
        mWhitePaint.setTextSize(DensityUtil.dip2px(getContext(),12));
        ///初始化线条画笔
        mLinePaint=new Paint();
        mLinePaint.setTextAlign(Paint.Align.CENTER);
        mLinePaint.setColor(Color.parseColor("#00C1DE"));
        mLinePaint.setStrokeWidth(10);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    //画出step
    private void drawStep(Canvas canvas){
        int childWidth=width/stepSize;//将view的width N等分，每个圆圈的x坐标就在childWidth的2分之一处
        int circleY=radius+getPaddingTop();
        int firstPointX=childWidth/2;
        drawLine(canvas,0,firstPointX,circleY);
        int halfFontHeightOffset = -(int)(mWhitePaint.ascent() + mWhitePaint.descent()) / 2;
        for (int i = 1; i <=stepSize ; i++) {
             int circleX=childWidth*i-childWidth/2;
             int index=i-1;
             int lineCenterX=childWidth*i;//
             int halfLineLength = childWidth / 2 - radius;//线段长度2分一
             if(i<currentStep){
                 mCirclePaint.setColor(Color.parseColor("#00C1DE"));
                 mBluePaint.setColor(Color.parseColor("#00C1DE"));
                 mLinePaint.setColor(Color.parseColor("#00C1DE"));
             }else{
                 mCirclePaint.setColor(Color.parseColor("#DDDDDD"));
                 mBluePaint.setColor(Color.parseColor("#DDDDDD"));
                 mLinePaint.setColor(Color.parseColor("#DDDDDD"));
                 if(i==currentStep){
                     mCirclePaint.setColor(Color.parseColor("#00C1DE"));
                 }
             }
             canvas.drawCircle(circleX,circleY,radius,mCirclePaint);
             drawWhiteText(canvas,i+"",circleX,circleY+halfFontHeightOffset);
             drawBlueText(canvas,mSteps.get(index),circleX,circleY+radius+getBlueTextHeight());
             drawLine(canvas,lineCenterX-halfLineLength,lineCenterX+halfLineLength,circleY);
        }
    }
    //画出蓝色文字
    private void drawBlueText(Canvas canvas,String stepString,int x,int y){
        mBluePaint.setFakeBoldText(false);
        canvas.drawText(stepString,x,y,mBluePaint);
    }
    //画出白色文字
    private void drawWhiteText(Canvas canvas,String stepString,int x,int y){
        mWhitePaint.setFakeBoldText(true);
        canvas.drawText(stepString,x,y,mWhitePaint);
    }
    //画线
    private void drawLine(Canvas canvas,int startX,int endX,int centY){
        canvas.drawLine(startX,centY,endX,centY,mLinePaint);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode==MeasureSpec.AT_MOST){//wrapcontent模式需要处理一下高度
            height=getPaddingTop()+getPaddingBottom()+radius*2+getBlueTextHeight();//上下的填充加上直径再加上文字高度就是view的高度
        }
        setMeasuredDimension(width,height);
    }
    //绘制思路，首先确认每个点的x,y坐标，
    // 将view的宽度（默认屏幕宽度）N等分
    @Override
    protected void onDraw(Canvas canvas) {
        drawStep(canvas);
    }
    //计算蓝色文字高度
    private int getBlueTextHeight(){
        textHeight=(int)Math.ceil(mBluePaint.descent()-mBluePaint.ascent());
        return textHeight;
    }
}
