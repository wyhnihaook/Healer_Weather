package com.sjxz.moji_weather.mvp.interactor.impl;

import com.sjxz.moji_weather.bean.SortModel;
import com.sjxz.moji_weather.helper.pinyin.CharacterParser;
import com.sjxz.moji_weather.mvp.interactor.SelectCityInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/7.
 * Role:
 */
public class SelectCityInteractorImpl implements SelectCityInteractor {
    @Override
    public List<SortModel> getData(String[] data) {
        List<SortModel> listarray = new ArrayList<SortModel>();
        for (int i = 0; i < data.length; i++) {
            String pinyin = CharacterParser.getPingYin(data[i]);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();
            SortModel person = new SortModel();
            person.setName(data[i]);
            //person.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                person.setSortLetters(Fpinyin);
            } else {
                person.setSortLetters("#");
            }
            listarray.add(person);
        }
        return listarray;
    }
}
