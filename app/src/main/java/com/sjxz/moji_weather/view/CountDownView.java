package com.sjxz.moji_weather.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sjxz.moji_weather.R;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/7.
 * Role:
 */
public class CountDownView extends View {

    private static int defaultCircleSolideColor = Color.BLUE;
    private static int defaultCircleStrokeColor = Color.WHITE;//最底层的颜色
    private static int defaultCircleStrokeWidth = 10;
    private static int defaultCircleRadius = 60;
    private static int progressColor = Color.GRAY;//进度条的颜色
    private static int progressWidth = 11;//>defaultCircleStrokeWidth
    private static int smallCircleSolideColor = Color.BLACK;
    private static int smallCircleStrokeColor = Color.WHITE;
    private static float smallCircleStrokeWidth = 8;
    private static float smallCircleRadius = 30;
    private static int textColor = Color.WHITE;//BLACK
    private static float textSize = 30;
    private static Paint defaultCriclePaint;
    private static Paint progressPaint;
    private static Paint textPaint;
    private static float currentAngle;
    public static String textDesc;
    public static long countdownTime;
    private static int mStartSweepValue = -90;

    //设置画布圆形背景
    private Paint backgroundPaint;
    public  ValueAnimator animator;

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle(attrs);
        setPaint();
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        initStyle(attrs);
        setPaint();
    }

    private void initStyle(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownProgress);
        int indexCount = typedArray.getIndexCount();
        for(int i=0;i<indexCount;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CountDownProgress_default_circle_solide_color:
                    defaultCircleSolideColor = typedArray.getColor(attr, defaultCircleSolideColor);
                    break;
                case R.styleable.CountDownProgress_default_circle_stroke_color:
                    defaultCircleStrokeColor = typedArray.getColor(attr, defaultCircleStrokeColor);
                    break;
                case R.styleable.CountDownProgress_default_circle_stroke_width:
                    defaultCircleStrokeWidth = (int) typedArray.getDimension(attr, defaultCircleStrokeWidth);
                    break;
                case R.styleable.CountDownProgress_default_circle_radius:
                    defaultCircleRadius = (int) typedArray.getDimension(attr, defaultCircleRadius);
                    break;
                case R.styleable.CountDownProgress_progress_color:
                    progressColor = typedArray.getColor(attr, progressColor);
                    break;
                case R.styleable.CountDownProgress_progress_width:
                    progressWidth = (int) typedArray.getDimension(attr, progressWidth);
                    break;
                case R.styleable.CountDownProgress_small_circle_solide_color:
                    smallCircleSolideColor = typedArray.getColor(attr, smallCircleSolideColor);
                    break;
                case R.styleable.CountDownProgress_small_circle_stroke_color:
                    smallCircleStrokeColor = typedArray.getColor(attr, smallCircleStrokeColor);
                    break;
                case R.styleable.CountDownProgress_small_circle_stroke_width:
                    smallCircleStrokeWidth = (int) typedArray.getDimension(attr, smallCircleStrokeWidth);
                    break;
                case R.styleable.CountDownProgress_small_circle_radius:
                    smallCircleRadius = (int) typedArray.getDimension(attr, smallCircleRadius);
                    break;
                case R.styleable.CountDownProgress_text_color:
                    textColor = typedArray.getColor(attr, textColor);
                    break;
                case R.styleable.CountDownProgress_text_size:
                    textSize = (int) typedArray.getDimension(attr, textSize);
                    break;
            }
        }
        typedArray.recycle();

    }

    private void setPaint() {
        //默认圆
        defaultCriclePaint = new Paint();
        defaultCriclePaint.setAntiAlias(true);//抗锯齿
        defaultCriclePaint.setDither(true);//防抖动
        defaultCriclePaint.setStyle(Paint.Style.STROKE);
        defaultCriclePaint.setStrokeWidth(defaultCircleStrokeWidth);
        defaultCriclePaint.setColor(defaultCircleStrokeColor);//这里先画边框的颜色，后续再添加画笔画实心的颜色
        //默认圆上面的进度弧度
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setDither(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔笔刷样式

        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        backgroundPaint=new Paint();
        backgroundPaint.setAntiAlias(true);//抗锯齿
        backgroundPaint.setDither(true);//防抖动
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setStrokeWidth(defaultCircleStrokeWidth);
        backgroundPaint.setColor(progressColor);//这里先画边框的颜色，后续再添加画笔画实心的颜色

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        //背景圆
        canvas.drawCircle(defaultCircleRadius, defaultCircleRadius, defaultCircleRadius, backgroundPaint);

        //画默认圆
        canvas.drawCircle(defaultCircleRadius, defaultCircleRadius, defaultCircleRadius, defaultCriclePaint);
        //画进度圆弧
        //currentAngle = getProgress()*1.0f/getMax()*360;recf适用于划出一块绘制的区域mStartSweepValue是开始的位置
        canvas.drawArc(new RectF(0, 0, defaultCircleRadius*2, defaultCircleRadius*2),mStartSweepValue, 360*currentAngle,false,progressPaint);
        //画中间文字
        //   String text = getProgress()+"%";
        //获取文字的长度的方法
        float textWidth = textPaint.measureText(textDesc);
        float textHeight = (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText(textDesc, defaultCircleRadius - textWidth/2, defaultCircleRadius - textHeight, textPaint);

        canvas.restore();

    }

    /**
     * 如果该View布局的宽高开发者没有精确的告诉，则需要进行测量，如果给出了精确的宽高则我们就不管了
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;
        int strokeWidth = Math.max(defaultCircleStrokeWidth, progressWidth);
        //精确指定宽高
        if(widthMode != MeasureSpec.EXACTLY){
            widthSize = getPaddingLeft() + defaultCircleRadius*2 + strokeWidth + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if(heightMode != MeasureSpec.EXACTLY){
            heightSize = getPaddingTop() + defaultCircleRadius*2 + strokeWidth + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //属性动画
    public void startCountDownTime(final OnCountdownFinishListener countdownFinishListener){
        setClickable(true);
        animator = ValueAnimator.ofFloat(0, 1.0f);
        //动画时长，让进度条在CountDown时间内正好从0-360走完，这里由于用的是CountDownTimer定时器，倒计时要想减到0则总时长需要多加1000毫秒，所以这里时间也跟着+1000ms
        animator.setDuration(countdownTime );//+ 1000
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(0);//表示不循环，-1表示无限循环
        //值从0-1.0F 的动画，动画时长为countdownTime，ValueAnimator没有跟任何的控件相关联，那也正好说明ValueAnimator只是对值做动画运算，而不是针对控件的，我们需要监听ValueAnimator的动画过程来自己对控件做操作
        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                 * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                 * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                 */
                currentAngle = (float) animation.getAnimatedValue();
                //       Log.e("currentAngle",currentAngle+"");
                invalidate();//实时刷新view，这样我们的进度条弧度就动起来了
            }
        });
        //开启动画
        animator.start();
        //还需要另一个监听，监听动画状态的监听器
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
                if(countdownFinishListener != null){
                    countdownFinishListener.countdownFinished();
                }
                if(countdownTime > 0){
                    setClickable(true);
                }else{
                    setClickable(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        //调用倒计时操作
        countdownMethod();
    }

    public CountDownTimer timer;

    //倒计时的方法
    private void countdownMethod(){
        timer= new CountDownTimer(countdownTime+1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //         Log.e("time",countdownTime+"");
                countdownTime = countdownTime-1000;
                textDesc = "跳过("+((countdownTime/1000)) + ")";

                //countdownTime = countdownTime-1000;
                //刷新view
                invalidate();
            }
            @Override
            public void onFinish() {
                //textDesc = 0 + "″";
                //刷新view
//                textDesc="跳过(0)";
//                invalidate();
            }
        }.start();
    }
    public void setCountdownTime(long countdownTime){
        this.countdownTime = countdownTime;
        textDesc = countdownTime / 1000 + "″";
    }

    public interface OnCountdownFinishListener{
        void countdownFinished();
    }


}

