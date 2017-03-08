package com.sjxz.moji_weather.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.activity.BlankActivity;
import com.sjxz.moji_weather.base.SimpleBackPage;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/7.
 * Role:
 */
public class UIHelper {

    /**
     * 设置标题
     *
     * @param activity
     * @param title
     */
    public static void setTitle(Activity activity, String title) {
        TextView tv_title = (TextView) activity.findViewById(R.id.title);
        tv_title.setText(title);
    }

    /**
     * 设置顶部返回键
     * **/
    public static void setLeftBack(final Activity activity){
        ImageButton imageButton = (ImageButton) activity.findViewById(R.id.ImageButton1);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public static void jumpBlankAcitivty(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, BlankActivity.class);
        intent.putExtra(BlankActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

}
