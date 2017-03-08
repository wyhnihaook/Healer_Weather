package com.sjxz.moji_weather.bean.weather;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/23.
 * Role:
 */
public class AlarmList {
    private String signalLevel;

    private String issueContent;

    private String issueTime;

    private String province;

    private String signalType;

    private String city;

    public void setSignalLevel(String signalLevel){
        this.signalLevel = signalLevel;
    }
    public String getSignalLevel(){
        return this.signalLevel;
    }
    public void setIssueContent(String issueContent){
        this.issueContent = issueContent;
    }
    public String getIssueContent(){
        return this.issueContent;
    }
    public void setIssueTime(String issueTime){
        this.issueTime = issueTime;
    }
    public String getIssueTime(){
        return this.issueTime;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getProvince(){
        return this.province;
    }
    public void setSignalType(String signalType){
        this.signalType = signalType;
    }
    public String getSignalType(){
        return this.signalType;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }

}
