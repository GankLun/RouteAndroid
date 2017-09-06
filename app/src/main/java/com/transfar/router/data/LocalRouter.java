/**
 * @Package com.transfar.router.link
 * @Title: LocalRouter
 * @Description (Android端本地数据路由引擎)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Android端本地数据路由引擎
 * Created by czl on 2017/4/18.
 */

public class LocalRouter {

    private HashMap<String, Provider> mProviders = null;


    private LocalRouter() {
        mProviders = new HashMap<>();

    }

    private static class Instance {
        private static LocalRouter instance = new LocalRouter();
    }

    /**
     * 获取数据路由引擎实例
     *
     * @return 数据路由引擎实例
     */
    public static LocalRouter getInstance() {
        return Instance.instance;
    }


    /**
     * 注册数据路由provider
     *
     * @param providerName provider名称
     * @param provider     具体的Provider实例
     */
    public void registerProvider(String providerName, Provider provider) {
        mProviders.put(providerName, provider);
    }

    /**
     * 注销数据路由provider
     *
     * @param providerName provider名称
     */
    public void unRegisterProvider(String providerName) {
        if (mProviders != null) {
            if (mProviders.containsKey(providerName)) {
                mProviders.get(providerName).unRegisterAllAction();
                mProviders.remove(providerName);
            }
        }
    }

    /**
     * 同步调用路由
     *
     * @param context      上下文
     * @param routeRequest 路由请求封装对象实例
     * @return RouteResponse响应结果实例
     */
    public RouteResponse route(Context context, @NonNull RouteRequest routeRequest) {
        IAction targetAction = findRequestAction(routeRequest);
        return targetAction.invoke(context, routeRequest.getData());
    }

    /**
     * 异步调用路由
     *
     * @param context             上下文
     * @param routeRequest        路由请求封装对象实例
     * @param routeResultListener 路由回调监听器
     */
    public void asyncRoute(Context context, @NonNull RouteRequest routeRequest, RouteResultListener routeResultListener) {
        IAction targetAction = findRequestAction(routeRequest);
        targetAction.asyncInvoke(context, routeRequest.getData(), routeResultListener);
    }


    private IAction findRequestAction(RouteRequest routerRequest) {
        Provider targetProvider = mProviders.get(routerRequest.getProvider());
        ErrorAction defaultNotFoundAction = new ErrorAction();
        if (null == targetProvider) {
            return defaultNotFoundAction;
        } else {
            IAction targetAction = targetProvider.findAction(routerRequest.getAction());
            if (null == targetAction) {
                return defaultNotFoundAction;
            } else {
                return targetAction;
            }
        }
    }

}
