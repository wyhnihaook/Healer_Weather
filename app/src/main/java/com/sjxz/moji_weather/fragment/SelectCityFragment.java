package com.sjxz.moji_weather.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.adapter.SortAdapter;
import com.sjxz.moji_weather.base.BaseFragment;
import com.sjxz.moji_weather.bean.SortModel;
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.helper.UIHelper;
import com.sjxz.moji_weather.helper.pinyin.ClearEditText;
import com.sjxz.moji_weather.helper.pinyin.SideBar;
import com.sjxz.moji_weather.mvp.presenter.impl.SelectCityPresenterImpl;
import com.sjxz.moji_weather.mvp.view.SelectCityView;
import com.sjxz.moji_weather.util.Constants;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/7.
 * Role:选择城市
 */
public class SelectCityFragment  extends BaseFragment implements SelectCityView, TextWatcher {


    @Bind(R.id.filter_edit)
    ClearEditText filter_edit;

    @Bind(R.id.country_lvcountry)
    ListView sortListView;

    @Bind(R.id.title_layout)
    LinearLayout title_layout;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.dialog)
    TextView dialog;

    @Bind(R.id.sidrbar)
    SideBar sideBar;


    private SortAdapter adapter;
    private List<SortModel> SourceDateList;

    private SelectCityPresenterImpl selectCityPresenter;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_select_city;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        UIHelper.setTitle(getActivity(),"添加城市");

        selectCityPresenter=new SelectCityPresenterImpl(this,getActivity());
        selectCityPresenter.initialMain();

        initData();
    }

    private void initData() {
        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });


        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                Toast.makeText(getActivity(), ((SortModel) adapter.getItem(position)).getName() + "=" + position, Toast.LENGTH_SHORT).show();

                    //添加该城市的名称，数据更新
                EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_ADD_CITY,((SortModel) adapter.getItem(position)).getName()));
                getActivity().finish();
            }
        });


        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

//                LogUtils.i(+visibleItemCount+"=当前对呀的Item是="+firstVisibleItem);

                //字母连续断层使不能置顶，例如  D （空） F使D到F阶段不存在置顶
                int section;
                try {
                    section = adapter.getSectionForPosition(firstVisibleItem);
                } catch (Exception e) {
                    return;
                }
                int nextSecPosition = adapter.getPositionForSection(section + 1);
                //解决断层置顶
                for (int i = 1; i < 30; i++) {
                    //26个英文字母充分循环
                    if (nextSecPosition == -1) {
                        //继续累加
                        int data = section + 1 + i;
                        nextSecPosition = adapter.getPositionForSection(data);
                    } else {
                        break;
                    }
                }

                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) title_layout.getLayoutParams();
                    params.topMargin = 0;
                    title_layout.setLayoutParams(params);
                    title.setText(String.valueOf((char) section));

                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = title_layout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) title_layout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            title_layout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                title_layout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });


        filter_edit.addTextChangedListener(this);

    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void initialAllCityData(List<SortModel> list) {
        SourceDateList=list;
        Collections.sort(SourceDateList, selectCityPresenter.pinyinComparator);
        adapter = new SortAdapter(getActivity(), SourceDateList);
        sortListView.setAdapter(adapter);
    }

    @Override
    public void filterCityData(List<SortModel> listFilter) {
        adapter.updateListView(listFilter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        selectCityPresenter.filterData(s.toString(),title_layout,SourceDateList,title);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
