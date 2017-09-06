/**
 * @Package com.transfar.router.link
 * @Title: Provider
 * @Description (抽象服务提供者)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.data;

import java.util.HashMap;

/**
 * 抽象服务提供者
 * Created by czl on 2017/4/18.
 */

public abstract class Provider {
    private HashMap<String, IAction> mActions;

    /**
     * 无参构造方法
     */
    public Provider() {
        mActions = new HashMap<>();
        registerActions();
    }

    /**
     * 注册行为逻辑
     *
     * @param actionName 行为逻辑名称
     * @param action     具体行为逻辑实现类
     */
    protected final void registerAction(String actionName, IAction action) {
        mActions.put(actionName, action);
    }

    /**
     * 根据名称查找对应的行为逻辑实现类
     *
     * @param actionName 行为逻辑名称
     * @return 具体的行为逻辑实现
     */
    protected final IAction findAction(String actionName) {
        return mActions.get(actionName);
    }

    /**
     * 抽象方法，注册行为逻辑
     */
    protected abstract void registerActions();

     void unRegisterAllAction() {
        if (mActions != null) {
            mActions.clear();
        }
    }

}
