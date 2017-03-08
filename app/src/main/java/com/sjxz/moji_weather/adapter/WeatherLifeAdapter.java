package com.sjxz.moji_weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.bean.weather.WeatherBaseLifeBean;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/12/1.
 * Role:
 */
public class WeatherLifeAdapter extends BaseRecyclerAdapter {


    private Context context;

    public WeatherLifeAdapter(Context context) {
        this.context = context;
    }

    public class WeatherLifeHolder extends RecyclerView.ViewHolder {

        public ImageView life_icon;

        public TextView life_title;

        public TextView life_result;

        public WeatherLifeHolder(View itemView) {
            super(itemView);

            life_icon = (ImageView) itemView.findViewById(R.id.life_icon);

            life_title = (TextView) itemView.findViewById(R.id.life_title);

            life_result = (TextView) itemView.findViewById(R.id.life_result);
        }
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return new WeatherLifeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, boolean isItem) {
        WeatherLifeHolder holder = (WeatherLifeHolder) viewHolder;

        WeatherBaseLifeBean item = (WeatherBaseLifeBean) getDatas().get(position);
        //图片初始化
        switch (item.getCheck()) {
            case 0:
                holder.life_icon.setImageResource(R.mipmap.beauty);
                holder.life_result.setText("保湿情况");
                break;
            case 1:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_drsg);
                holder.life_result.setText("天气情况");
                break;
            case 2:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_flu);
                holder.life_result.setText("流行病");
                break;
            case 3:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_comf);
                holder.life_result.setText("舒适度");
                break;
            case 4:
                holder.life_icon.setImageResource(R.mipmap.glass);
                holder.life_result.setText("太阳镜");
                break;
            case 5:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_sport);
                holder.life_result.setText("运动");
                break;
            case 6:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_trav);
                holder.life_result.setText("旅游");
                break;
            case 7:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_uv);
                holder.life_result.setText("紫外线");
                break;
            case 8:
                holder.life_icon.setImageResource(R.mipmap.ic_suggestion_cw);
                holder.life_result.setText("洗车");
                break;
        }

        if(item.getTitle()!=null){
            holder.life_title.setText(item.getTitle());
        }else{
            holder.life_title.setText("暂缺");
        }

//        if(item.getDesc()!=null){
//            holder.life_result.setText(item.getDesc());
//        }


    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.layout_weather_life, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new WeatherLifeHolder(realContentView);
    }

    @Override
    public int getAdapterItemCount() {
        return getDatas().size();
    }

}
