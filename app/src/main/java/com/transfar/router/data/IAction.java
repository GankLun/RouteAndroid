/**
 * @Package com.transfar.router.data
 * @Title: IAction
 * @Description (具体逻辑行为接口)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/16
 * @Author czl 2017/6/16
 */

package com.transfar.router.data;

import android.content.Context;

import java.util.HashMap;


/**
 * 具体逻辑行为接口
 * Created by czl on 2017/6/16
 */

public interface IAction {
    /**
     * 同步方法
     *
     * @param context     上下文
     * @param requestData 请求参数
     * @return RouteResponse对象
     */
    RouteResponse invoke(Context context, HashMap<String, Object> requestData);

    /**
     * 异步方法
     *
     * @param context     上下文
     * @param requestData 请求参数
     * @param listener    回调监听
     */
    void asyncInvoke(final Context context, final HashMap<String, Object> requestData, final RouteResultListener listener);
}
