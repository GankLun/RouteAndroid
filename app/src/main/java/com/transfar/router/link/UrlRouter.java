/**
 * @Package com.transfar.router.link
 * @Title: UrlRouter
 * @Description (android端用于路由跳转中间件, 支持配置跳转动画、是否包外跳转、RequestCode、参数传递, flag)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.link;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Android端用于路由跳转中间件代理类
 * Created by czl 2017/6/13
 */
public class UrlRouter {
    public static final String URL_ROUTER = "UrlRouter";
    private Object mContext;
    private Intent mIntent;
    private boolean isAllowEscape;
    private int mRequestCode;

    private String mCategory;
    private int[] mTransitionAnim;
    private int mFlags;

    private UrlRouter(Object context) {
        if (context == null) throw new IllegalArgumentException("context can not be null!");
        if ((!(context instanceof Fragment)) && (!(context instanceof Activity)) && (!(context instanceof Application)) && (!
                (context instanceof Context))) {
            throw new IllegalArgumentException("context only can be Context instance!");
        }
        this.mContext = context;
        this.isAllowEscape = false;
        this.mRequestCode = -1;
        this.mIntent = new Intent("com.transfar.route");
        this.mIntent.addCategory(Intent.CATEGORY_DEFAULT);
        if (context instanceof Context) {
            UrlRouterUtil.setupReferrer((Context) context, mIntent);
        }

    }

    /**
     * 构造默认路由跳转器
     *
     * @param context 上下文
     * @return UrlRouter对象
     */
    public static UrlRouter from(Object context) {
        return new UrlRouter(context);
    }

    /**
     * 配置跳转参数
     *
     * @param bundle 参数封装对象
     * @return UrlRouter对象
     */
    public final UrlRouter params(Bundle bundle) {
        if (bundle == null) return this;
        mIntent.putExtras(bundle);
        return this;
    }

    /**
     * 配置跳转类别
     *
     * @param category 配置类别
     * @return UrlRouter对象
     */
    public final UrlRouter category(String category) {
        mCategory = category;
        return this;
    }

    /**
     * 配置跳转动画
     *
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     * @return UrlRouter对象
     */
    public final UrlRouter transitionAnim(int enterAnim, int exitAnim) {
        if (enterAnim < 0 || exitAnim < 0) {
            mTransitionAnim = null;
            return this;
        }
        mTransitionAnim = new int[2];
        mTransitionAnim[0] = enterAnim;
        mTransitionAnim[1] = exitAnim;
        return this;
    }

    /**
     * 配置requestCode
     *
     * @param reqCode 默认值为-1，如果大于等于0，已startActivityForResult方式启动，否则startActivity方式启动
     * @return UrlRouter对象
     */
    public final UrlRouter requestCode(int reqCode) {
        mRequestCode = reqCode;
        return this;
    }

    /**
     * 跳转目标组件
     *
     * @param url 跳转url
     * @return 布尔值 true代表跳转成功，false代表跳转失败
     */
    public final boolean jump(String url) {
        return !TextUtils.isEmpty(url) && jump(Uri.parse(url));
    }

    /**
     * 允许启动非本应用包名组件
     *
     * @return UrlRouter对象
     */
    public final UrlRouter allowEscape() {
        isAllowEscape = true;
        return this;
    }

    /**
     * 不允许启动非本应用包名组件
     *
     * @return UrlRouter对象
     */
    public final UrlRouter forbidEscape() {
        isAllowEscape = false;
        return this;
    }

    /**
     * 配置跳转模式
     *
     * @param flags 跳转模式
     * @return UrlRouter对象
     */
    public final UrlRouter flags(int flags) {
        mFlags = flags;
        return this;
    }

    /**
     * 跳转目标组件
     *
     * @param uri 跳转uri
     * @return 布尔值 true代表跳转成功，false代表跳转失败
     */
    public final boolean jump(Uri uri) {
        if (uri == null) return false;
        if (!isAllowEscape) {
            if (mContext instanceof Fragment) {
                mIntent.setPackage(((Fragment) mContext).getActivity().getApplicationContext().getPackageName());
            } else {
                mIntent.setPackage(((Context) mContext).getApplicationContext().getPackageName());
            }

        }
        if (!TextUtils.isEmpty(mCategory)) {
            mIntent.addCategory(mCategory);
        }


        mIntent.setData(uri);
        Bundle bundle = mIntent.getExtras();
        Bundle finalBundle = UrlRouterUtil.getParams(uri, bundle);
        mIntent.replaceExtras(finalBundle);
        ResolveInfo targetActivity;
        if (mContext instanceof Fragment) {
            targetActivity = UrlRouterUtil.queryActivity(((Fragment) mContext).getActivity(), mIntent);
        } else {
            targetActivity = UrlRouterUtil.queryActivity((Context) mContext, mIntent);
        }
        if (targetActivity == null) return false;
        if (mContext instanceof Fragment) {
            if (mFlags != 0) {
                mIntent.setFlags(mFlags);
            }
            if (mRequestCode >= 0) {
                Fragment fragment = (Fragment) mContext;
                fragment.startActivityForResult(mIntent, mRequestCode);
            } else {
                Fragment fragment = (Fragment) mContext;
                fragment.startActivity(mIntent);
            }
            if (mTransitionAnim != null) {
                Fragment fragment = (Fragment) mContext;
                fragment.getActivity().overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
            }
            return true;
        }
        if (!(mContext instanceof Activity)) {
            mIntent.setFlags(mFlags == 0 ? Intent.FLAG_ACTIVITY_NEW_TASK : mFlags);
            ContextCompat.startActivities((Context) mContext, new Intent[]{mIntent});
        } else {
            if (mFlags != 0) {
                mIntent.setFlags(mFlags);
            }
            if (mRequestCode >= 0) {
                ActivityCompat.startActivityForResult((Activity) mContext, mIntent, mRequestCode, null);
            } else {
                ActivityCompat.startActivity((Activity) mContext, mIntent, null);
            }
            if (mTransitionAnim != null) {
                ((Activity) mContext).overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
            }
        }
        return true;
    }

    /**
     * 跳转启动页面
     *
     * @param url 跳转启动页url
     * @return 布尔值 true代表跳转成功，false代表跳转失败
     */
    public final boolean jumpToMain(String url) {
        return !TextUtils.isEmpty(url) && jumpToMain(Uri.parse(url));
    }


    /**
     * 跳转启动页面
     *
     * @param uri 跳转启动页uri
     * @return 布尔值 true代表跳转成功，false代表跳转失败
     */
    public final boolean jumpToMain(Uri uri) {
        mIntent.setAction(Intent.ACTION_MAIN);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return jump(uri);
    }

    /**
     * 获取前一个activity对应的路由信息
     *
     * @param context
     * @return Routed对象
     */
    public static Route getStartedRoute(Context context) {
        return UrlRouterUtil.parseStartedRoute(context);
    }

    /**
     * 获取当前activity对应的路由信息
     *
     * @param context
     * @return Routed对象
     */
    public static Route getCurrentRoute(Context context) {
        return UrlRouterUtil.parseCurrentRoute(context);
    }

}
