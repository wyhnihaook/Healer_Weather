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
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.util.Constants;

import de.greenrobot.event.EventBus;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/15.
 * Role:
 */
public class AllCityAdapter extends BaseRecyclerAdapter {

    private Context context;

    public AllCityAdapter(Context context) {
        this.context = context;
    }

    public class AllCityHolder extends RecyclerView.ViewHolder{

        TextView city_name;

        TextView delete_city;

        ImageView more_message;



        public AllCityHolder(View itemView) {
            super(itemView);
            city_name=(TextView)itemView.findViewById(R.id.city_name);
            delete_city=(TextView)itemView.findViewById(R.id.delete_city);
            more_message=(ImageView) itemView.findViewById(R.id.more_message);
        }
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return new AllCityHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, boolean isItem) {

        final AllCityHolder holder=(AllCityHolder)viewHolder;

        final String cityName= (String) getDatas().get(position);
        holder.delete_city.setVisibility(View.GONE);
        holder.city_name.setText(cityName);
        holder.more_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    holder.delete_city.setVisibility( holder.delete_city.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
            }
        });
        holder.delete_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除该城市传递数据
                if(getDatas().size()>1){
                    getDatas().remove(position);
                    notifyDataSetChanged();
                }

                EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_DELETE_CITY,cityName));
            }
        });
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.layout_all_city, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new AllCityHolder(realContentView);
    }

    @Override
    public int getAdapterItemCount() {
        return getDatas().size();
    }
}
