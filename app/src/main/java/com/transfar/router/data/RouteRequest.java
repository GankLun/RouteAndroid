/**
 * @Package com.transfar.router.link
 * @Title: RouteRequest
 * @Description (业务请求对象)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务请求对象
 * Created by czl on 2017/4/18.
 */

public class RouteRequest {
    private String provider;
    private String action;
    private HashMap<String, Object> data;


    private RouteRequest(Builder builder) {
        this.provider = builder.mProvider;
        this.action = builder.mAction;
        this.data = builder.mData;
    }

    /**
     *  获取当前provider名称
     *  @return provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     *  获取当前Action名称
     *  @return action
     */
    public String getAction() {
        return action;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    /**
     * 获取当前RouteRequest信息对应的json信息
     * @return 以json形式返回当前RouteRequest信息
     */
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("provider", provider);
            jsonObject.put("action", action);

            try {
                JSONObject jsonData = new JSONObject();
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    jsonData.put(entry.getKey(), entry.getValue());
                }
                jsonObject.put("data", jsonData);
            } catch (Exception e) {
                e.printStackTrace();
                jsonObject.put("data", "{}");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    /**
     * RouteRequest构造者
     */
    public static class Builder {
        private String mProvider;
        private String mAction;
        private HashMap<String, Object> mData;

        /**
         * 构造方法，初始化
         */
        public Builder() {
            mProvider = "";
            mAction = "";
            mData = new HashMap<>();
        }

        /**
         * 设置provider名称
         * @param provider provider名称
         * @return provider构造者
         */
        public Builder provider(String provider) {
            this.mProvider = provider;
            return this;
        }

        /**
         * 设置action名称
         * @param action action名称
         * @return provider构造者
         */
        public Builder action(String action) {
            this.mAction = action;
            return this;
        }

        /**
         * 设置传递参数
         * @param data 传递参数
         * @return provider构造者
         */
        public Builder data(HashMap<String, Object> data) {
            this.mData = data;
            return this;
        }

        /**
         * 构造RouteRequest业务请求对象
         * @return RouteRequest业务请求对象
         */
        public RouteRequest build() {
            return new RouteRequest(this);
        }
    }

}
