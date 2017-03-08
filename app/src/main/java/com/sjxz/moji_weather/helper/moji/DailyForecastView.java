package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.bean.weather.DailyForecastBean;
import com.sjxz.moji_weather.util.Utils;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/30.
 * Role:一周的天气预报(自定义view样式曲线)
 */
public class DailyForecastView extends View {

    private int width, height;//宽度，高度
    private float percent = 0f;
    ;//当前的百分比
    private float density;

    private Path tmpMaxPath = new Path();//绘制路线
    private Path tmpMinPath = new Path();

    private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);//text的画笔,抗锯齿
    private final Paint paintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
    private final Paint paintOrange = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿


    private List<DailyForecastBean> forecastList;

    private Bitmap[] mImgsBitmap;//图片预加载,减少延迟
    private Data[] datas;
    /**
     * 对应的图片
     */
    private int[] mImgs = new int[]{R.drawable.w0, R.drawable.w2,
            R.drawable.w1, R.drawable.w7, R.drawable.w8,
            R.drawable.w6, R.drawable.w45, R.drawable.w14, R.drawable.w13,

            R.drawable.w30,
            R.drawable.w31, R.drawable.w34,

    };

    public class Data {
        public float minOffsetPercent, maxOffsetPercent;// 差值%
        public int tmp_max, tmp_min;
        public String date;
        public String wind_sc;
        public String cond_txt_d;

        public String monthandday;//几月几号
        public String day_weather;//早上的天气
        public String night_weather;//晚上的天气
        public String cloud_power;//风速
    }

    public DailyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        mImgsBitmap = new Bitmap[12];//---添加天气之后要再加常数
        for (int i = 0; i < mImgsBitmap.length; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }
        if (isInEditMode()) {
            return;
        }
        init(context);
    }

    private void init(Context context) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1f * density);
        paint.setTextSize(12f * density);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTypeface(MainActivity.getTypeface(context));
        paintBlue.setColor(getResources().getColor(R.color.material_deep_teal_20));
        paintBlue.setStrokeWidth(1f * density);
        paintBlue.setTextSize(12f * density);
        paintBlue.setStyle(Paint.Style.STROKE);
        paintBlue.setTextAlign(Paint.Align.CENTER);

        paintOrange.setColor(getResources().getColor(R.color.orange_light));
        paintOrange.setStrokeWidth(1f * density);
        paintOrange.setTextSize(12f * density);
        paintOrange.setStyle(Paint.Style.STROKE);
        paintOrange.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);//设置字体风格
        //一共需要 顶部文字2(+图占8行)+底部文字2 + 【间距1 + 日期1 + 间距0.5 +　晴1 + 间距0.5f + 微风1 + 底部边距1f 】 = 18行  +[同字]
        //                                  12     13       14      14.5    15.5      16      17       18
        final float textSize = this.height / 30f;
        paint.setTextSize(textSize);
        final float textOffset = getTextPaintOffset(paint);
        final float dH = textSize * 8f;
        final float dCenterY = textSize * 14f;
        if (datas == null || datas.length <= 1) {
            canvas.drawLine(0, dCenterY, this.width, dCenterY, paint);//没有数据的情况下只画一条线
            return;
        }
        final float dW = this.width * 1f / datas.length;
        tmpMaxPath.reset();
        tmpMinPath.reset();
        final int length = datas.length;
        float[] x = new float[length];
        float[] yMax = new float[length];
        float[] yMin = new float[length];

        final float textPercent = (percent >= 0.6f) ? ((percent - 0.6f) / 0.4f) : 0f;
        final float pathPercent = (percent >= 0.6f) ? 1f : (percent / 0.6f);

        //画底部的三行文字和标注最高最低温度
        paint.setAlpha((int) (255 * textPercent));
        for (int i = 0; i < length; i++) {
            final Data d = datas[i];
            x[i] = i * dW + dW / 2f;//画圆的中心点
            yMax[i] = dCenterY - d.maxOffsetPercent * dH;
            yMin[i] = dCenterY - d.minOffsetPercent * dH;

        }
        paint.setAlpha(255);

        for (int i = 0; i < (length - 1); i++) {
            final float midX = (x[i] + x[i + 1]) / 2f;
            final float midYMax = (yMax[i] + yMax[i + 1]) / 2f;
            final float midYMin = (yMin[i] + yMin[i + 1]) / 2f;
            if (i == 0) {
                tmpMaxPath.moveTo(0, yMax[i]);
                tmpMinPath.moveTo(0, yMin[i]);
            }
            tmpMaxPath.cubicTo(x[i] - 1, yMax[i], x[i], yMax[i], midX, midYMax);
//			tmpMaxPath.quadTo(x[i], yMax[i], midX, midYMax);
            tmpMinPath.cubicTo(x[i] - 1, yMin[i], x[i], yMin[i], midX, midYMin);
//			tmpMinPath.quadTo(x[i], yMin[i], midX, midYMin);

            if (i == (length - 2)) {
                tmpMaxPath.cubicTo(x[i + 1] - 1, yMax[i + 1], x[i + 1], yMax[i + 1], this.width, yMax[i + 1]);
                tmpMinPath.cubicTo(x[i + 1] - 1, yMin[i + 1], x[i + 1], yMin[i + 1], this.width, yMin[i + 1]);
            }
        }
        //draw max_tmp and min_tmp path
        paint.setStyle(Paint.Style.STROKE);
        final boolean needClip = pathPercent < 1f;
        if (needClip) {
            canvas.save();
            canvas.clipRect(0, 0, this.width * pathPercent, this.height);
            //canvas.drawColor(0x66ffffff);
        }
        canvas.drawPath(tmpMaxPath, paintOrange);
        canvas.drawPath(tmpMinPath, paintBlue);

        paint.setStyle(Paint.Style.FILL);//设置字体风格
        for (int i = 0; i < length; i++) {
            final Data d = datas[i];
            int CircleMaxY = 0;
            int CircleMaxYPre = 0;
            if (length >= 2 && i >= 1) {
                if ((datas[i - 1].tmp_max - d.tmp_max) > 3) {
                    CircleMaxY = 7*Math.abs(datas[i - 1].tmp_max - d.tmp_max)/3;
                }else if(-(datas[i - 1].tmp_max - d.tmp_max)>3){
                    CircleMaxY=-7*Math.abs(datas[i - 1].tmp_max - d.tmp_max)/3;
                }
                if (length > i + 1) {
                    if ((d.tmp_max - datas[i + 1].tmp_max) > 3) {
                        CircleMaxYPre = -4*Math.abs(d.tmp_max - datas[i + 1].tmp_max)/3;
                    }else if(-(d.tmp_max - datas[i + 1].tmp_max) > 3){
                        CircleMaxYPre = 4*Math.abs(d.tmp_max - datas[i + 1].tmp_max)/3;
                    }
                }
            }

            //上方地址
            canvas.drawText(d.tmp_max + "°", x[i], yMax[i] - textSize + textOffset+(CircleMaxY>0||CircleMaxYPre>0?-16:0), paint);// - textSize
            canvas.drawText(Utils.prettyDate(d.date), x[i], textSize + textOffset, paint);//日期d.date.substring(5)
            canvas.drawText(d.monthandday + "", x[i], textSize * 2.5f + textOffset, paint);//“晴"
            canvas.drawText(d.day_weather, x[i], textSize * 4f + textOffset, paint);//微风
            canvas.drawBitmap(Utils.big(checkDayWeather(d.day_weather), 80, 80), x[i] - 40, textSize * 5f + textOffset, paint);


            //下方数据
            canvas.drawText(d.tmp_min + "°", x[i], yMin[i] + textSize + textOffset, paint);
            canvas.drawBitmap(Utils.big(checkNightWeather(d.night_weather), 80, 80), x[i] - 40, textSize * 20.5f + textOffset, paint);
            canvas.drawText(d.night_weather, x[i], textSize * 24f + textOffset, paint);//日期d.date.substring(5)
            canvas.drawText(d.wind_sc + "", x[i], textSize * 25.5f + textOffset, paint);//“晴"
            canvas.drawText(d.cloud_power, x[i], textSize * 27f + textOffset, paint);//微风

            //圆点

            canvas.drawCircle(x[i], yMax[i]-CircleMaxYPre-CircleMaxY, 10, paint);
            canvas.drawCircle(x[i], yMin[i], 10, paint);
        }


        if (needClip) {
            canvas.restore();
        }
        if (percent < 1) {
            percent += 0.025f;// 0.025f;
            percent = Math.min(percent, 1f);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     *
     * R.drawable.w0, R.drawable.w2,
     R.drawable.w1, R.drawable.w7, R.drawable.w8,
     R.drawable.w6, R.drawable.w45, R.drawable.w14, R.drawable.w13,

     R.drawable.w30,
     R.drawable.w31, R.drawable.w34,
     * */
    public Bitmap checkDayWeather(String weather) {

        switch (weather) {
            case "晴":
                return mImgsBitmap[0];
            case "阴":
                return mImgsBitmap[1];
            case "多云":
                return mImgsBitmap[2];
            case "小雨":
                return mImgsBitmap[3];
            case "中雨":
                return mImgsBitmap[4];
            case "雨夹雪":
                return mImgsBitmap[5];
            case "霾":
                return mImgsBitmap[6];
            case "小雪":
                return mImgsBitmap[7];
            case "阵雪":
                return mImgsBitmap[8];

        }
        return BitmapFactory.decodeResource(getResources(), R.drawable.w0);
    }

    public Bitmap checkNightWeather(String weather) {

        switch (weather) {
            case "晴":
                return mImgsBitmap[9];
            case "阴":
                return mImgsBitmap[1];
            case "多云":
                return mImgsBitmap[10];
            case "小雨":
                return mImgsBitmap[3];
            case "中雨":
                return mImgsBitmap[4];
            case "雨夹雪":
                return mImgsBitmap[5];
            case "霾":
                return mImgsBitmap[6];
            case "小雪":
                return mImgsBitmap[7];
            case "阵雪":
                return mImgsBitmap[11];
        }
        return mImgsBitmap[0];
    }


    public void setData(List<DailyForecastBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }

        if (this.forecastList == list) {
            percent = 0f;
            invalidate();
            return;
        }

        this.forecastList = list;

        datas = new Data[forecastList.size()];

        try {
            int all_max = Integer.MIN_VALUE;
            int all_min = Integer.MAX_VALUE;
            for (int i = 0; i < forecastList.size(); i++) {
                DailyForecastBean forecast = forecastList.get(i);
                int max = Integer.valueOf(forecast.getTemperature_max());
                int min = Integer.valueOf(forecast.getTemperature_min());
                if (all_max < max) {
                    all_max = max;
                }
                if (all_min > min) {
                    all_min = min;
                }
                final Data data = new Data();
                data.tmp_max = max;
                data.tmp_min = min;
                data.date = forecast.getWeekday();
                data.wind_sc = forecast.getCloud_direction().equals("无持续风向")?"无风向":forecast.getCloud_direction();
                data.cond_txt_d = forecast.getDay_weather();
                data.monthandday = forecast.getDate();
                data.day_weather = forecast.getDay_weather();
                data.night_weather = forecast.getNight_weather();
                data.cloud_power = forecast.getCloud_speed();
                datas[i] = data;
            }
            float all_distance = Math.abs(all_max - all_min);
            float average_distance = (all_max + all_min) / 2f;
//		toast("all->" + all_distance + " aver->" + average_distance);
            for (Data d : datas) {
                d.maxOffsetPercent = (d.tmp_max - average_distance) / all_distance;
                d.minOffsetPercent = (d.tmp_min - average_distance) / all_distance;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		getGlobalVisibleRect(rect);
//		if(rect.isEmpty()){
//			percent = 1f;
//		}else{
        percent = 0f;
//		}
        invalidate();
    }


    /**
     * 进行尺寸匹配
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    public static float getTextPaintOffset(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return -(fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.top;
    }
}
