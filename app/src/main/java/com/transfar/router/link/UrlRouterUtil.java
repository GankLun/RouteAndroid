/**
 * @Package com.transfar.router.link
 * @Title: UrlRouterUtil
 * @Description (跳转路由工具实现类)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/6/13
 * @Author czl 2017/6/13
 */
package com.transfar.router.link;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Android 端跳转路由工具实现类
 * Created by czl 2017/6/13
 */
public class UrlRouterUtil {

    static void setupReferrer(Context context, Intent intent) {
        if (context != null && context instanceof Activity) {
            Route currentRoute = parseCurrentRoute(context);
            intent.putExtra(UrlRouter.URL_ROUTER, currentRoute);
        }
    }

    static Route parseStartedRoute(Context context) {
        if (context != null && context instanceof Activity) {
            Intent startedIntent = ((Activity) context).getIntent();
            if (startedIntent == null) {
                return null;
            }
            if (startedIntent.hasExtra(UrlRouter.URL_ROUTER)) {
                return startedIntent.getParcelableExtra(UrlRouter.URL_ROUTER);
            }
        }
        return null;
    }

    static Route parseCurrentRoute(Context context) {
        if (context != null && context instanceof Activity) {
            Route route = Route.newInstance();
            Intent startedIntent = ((Activity) context).getIntent();
            if (startedIntent == null) {
                return null;
            }
            Uri uri = startedIntent.getData();
            if (uri == null) return null;
            route.scheme = UrlRouterUtil.getScheme(uri);
            route.host = UrlRouterUtil.getHost(uri);
            route.path = UrlRouterUtil.getPath(uri);
            ResolveInfo resolveInfo = UrlRouterUtil.queryActivity(context, startedIntent);
            if (resolveInfo == null) return route;
            route.packageName = resolveInfo.activityInfo.packageName;
            route.activityName = resolveInfo.activityInfo.name;
            return route;
        }
        return null;
    }

    static ResolveInfo queryActivity(Context context, Intent intent) {
        if (context == null || intent == null) return null;
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        return packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

    }

    static String getScheme(Uri uri) {
        if (uri == null) return null;
        return uri.getScheme();
    }

    static String getHost(Uri uri) {
        if (uri == null) return null;
        return uri.getHost();
    }

    static String getPath(Uri uri) {
        if (uri == null) return null;
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) return null;
        path = path.replaceFirst("/", "");
        return path;
    }

    public static Bundle getParams(Uri uri, Bundle tempBundle) {
        Bundle bundle;
        if (tempBundle == null) {
            bundle = new Bundle();
        } else {
            bundle = tempBundle;
        }

        Set<String> keys = getQueryParameterNames(uri);
        String value;
        for (String str : keys) {
            value = uri.getQueryParameter(str);
            bundle.putString(str, value);
        }
        return bundle;
    }

    private static Set<String> getQueryParameterNames(Uri uri) {
        String query = uri.getEncodedQuery();
        if (query == null) {
            return Collections.emptySet();
        }
        Set<String> names = new LinkedHashSet<String>();
        int start = 0;
        do {
            int next = query.indexOf('&', start);
            int end = (next == -1) ? query.length() : next;

            int separator = query.indexOf('=', start);
            if (separator > end || separator == -1) {
                separator = end;
            }
            String name = query.substring(start, separator);
            names.add(Uri.decode(name));
            // Move start to end of name.
            start = end + 1;
        } while (start < query.length());
        return Collections.unmodifiableSet(names);
    }
}
