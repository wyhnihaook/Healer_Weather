package com.sjxz.moji_weather.mvp.view;

import com.sjxz.moji_weather.bean.SortModel;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/7.
 * Role://1.获取所有城市填充  2.获取更新过的数据
 */
public interface SelectCityView {

    void initialAllCityData(List<SortModel> list);

    void filterCityData(List<SortModel> listFilter);
}
