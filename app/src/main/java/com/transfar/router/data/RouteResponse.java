/**
 * @Package com.transfar.router.link
 * @Title: RouteResponse
 * @Description (路由业务逻辑结果对象)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 路由业务逻辑结果类
 * Created by czl on 2017/4/18.
 */

public class RouteResponse {
    /**
     * 表示成功
     */
    public static final int CODE_SUCCESS = 0;
    /**
     * 表示错误或者异常
     */
    public static final int CODE_ERROR = 1;
    /**
     * 表示无法找到相应的执行动作类
     */
    public static final int CODE_NOT_FOUND = 2;

    private int code;
    private String msg;
    private HashMap<String, Object> data;


    private RouteResponse(Builder builder) {
        this.code = builder.mCode;
        this.msg = builder.mMsg;
        this.data = builder.mData;

    }

    /**
     * 返回结果数据
     *
     * @return 结果数据
     */
    public HashMap<String, Object> getData() {
        return data;
    }

    /**
     * 返回响应结果码
     *
     * @return 响应结果码
     */
    public int getCode() {
        return code;
    }

    /**
     * 返回响应提示信息
     *
     * @return 结果提示信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 以json形式返回响应信息
     *
     * @return json形式返回响应信息
     */
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * RouteResponse构造者
     */
    public static class Builder {
        private int mCode;
        private String mMsg;
        private HashMap<String, Object> mData;

        /**
         * 构造方法
         */
        public Builder() {
            mCode = CODE_ERROR;
            mMsg = "";
            mData = null;
        }

        /**
         * 设置响应结果码
         *
         * @param code 响应结果码
         * @return RouteResponse构造者
         */
        public Builder code(int code) {
            this.mCode = code;
            return this;
        }

        /**
         * 设置响应提示信息
         *
         * @param msg 响应结果码
         * @return RouteResponse构造者
         */
        public Builder msg(String msg) {
            this.mMsg = msg;
            return this;
        }

        /**
         * 设置响应返回的结果数据
         *
         * @param data 响应返回的结果数据
         * @return RouteResponse构造者
         */
        public Builder data(HashMap<String, Object> data) {
            this.mData = data;
            return this;
        }

        /**
         * 构造RouteResponse响应结果对象
         *
         * @return RouteResponse响应结果对象
         */
        public RouteResponse build() {
            return new RouteResponse(this);
        }
    }


}
