<font color=red>内容</font>
# RouteAndroid
an android route library for page forwarding and data transmission across different component


![RouteAndroid](logo.jpg "RouteAndroid")


## page router by transfer bundle params

 UrlRouter.from(context).params(bundle).jump("activity://news/detail");
 

## page router by Animation

UrlRouter.from(context).transitionAnim(R.anim.enterAnim,R.anim.exitAnim).jump("activity://news/detail");
 

## page router startup like startActivityForResult

UrlRouter.from(context).requestCode(requestCode).jump("activity://news/detail");

## page router by custom launch mode

UrlRouter.from(context).flags(flags).jump("activity://news/detail");

## page router allows launch component with different application package
UrlRouter.from(context).allowEscape().jump("activity://news/detail");

## page router fobidden launch component with different application package
UrlRouter.from(context).forbidEscape().jump("activity://news/detail");

## page router statup launch activity
UrlRouter.from(context).jumpToMain("activity://news/detail");

## Documentation

- [Binary diff/patch utility](http://www.daemonology.net/bsdiff)
