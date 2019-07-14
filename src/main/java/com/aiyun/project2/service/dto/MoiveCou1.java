package com.aiyun.project2.service.dto;

public class MoiveCou1 {
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCinCou() {
        return cinCou;
    }

    public void setCinCou(int cinCou) {
        this.cinCou = cinCou;
    }

    public int getLocalmovieCou() {
        return localmovieCou;
    }

    public void setLocalmovieCou(int localmovieCou) {
        this.localmovieCou = localmovieCou;
    }

    //城市名称
    String cityName;
    //城市电影院总数
    int cinCou;
    //城市电影总数
    int localmovieCou;

    @Override
    public String toString() {
        return "MoiveCou1{" +
            "cityName='" + cityName + '\'' +
            ", cinCou=" + cinCou +
            ", localmovieCou=" + localmovieCou +
            '}';
    }

    public MoiveCou1() {
    }


}
