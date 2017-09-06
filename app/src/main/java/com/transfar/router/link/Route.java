/**
 * @Package com.transfar.router.link
 * @Title: Route
 * @Description (Android端路由实体类，封装关于路由activity的相关信息)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.link;

import android.os.Parcel;
import android.os.Parcelable;

/**Android端路由实体类，封装关于路由activity的相关信息
 * Created by czl 2017/6/13
 */
public final class Route implements Parcelable {
    public String scheme;
    public String host;
    public String path;
    public String packageName;
    public String activityName;

    private Route() {
    }

    static Route newInstance() {
        return new Route();
    }

    /**
     * 返回路由Activity信息
     * @return 返回路由Activity信息
     */
    @Override
    public String toString() {
        return "Route{" +
                "activityName='" + activityName + '\'' +
                ", scheme='" + scheme + '\'' +
                ", host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }

    /**
     * 重写 Parcelable接口
     * @return 描述符
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 重写 Parcelable接口,写入parcel
     * @param dest parcel序列化
     * @param flags 标识符
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.scheme);
        dest.writeString(this.host);
        dest.writeString(this.path);
        dest.writeString(this.packageName);
        dest.writeString(this.activityName);
    }

    /**
     * 构造方法
     * @param in parcel序列化
     */
    Route(Parcel in) {
        this.scheme = in.readString();
        this.host = in.readString();
        this.path = in.readString();
        this.packageName = in.readString();
        this.activityName = in.readString();
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
