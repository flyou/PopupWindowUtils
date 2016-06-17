# PopupWindowUtil

@(flyou)[PopupWindow|dialog|util]

**PopupWindowUtil**  is a small library for popupWindow,it can help you bulid your popWindow easy.
 -------------------

[TOC]

### Simple Use
``` java
 PopupWindowUtils
                .getInstance()
                .attachActivity(activity, parentView, attrView)
                .wiewSize(width, height
                .show();
```

### Or Detail
``` java
 PopupWindowUtils
                .getInstance()
                .attachActivity(MainActivity.this, R.layout.view_popwindow, v)
                .wiewSize(600, 900)
                .location(Gravity.CENTER)
                .focuseAble(true)
                .setPopupWindowClickListener(this)
                .setPopupWindowItemViewClickListener(this, R.id.cancel)
                .Animation(PopupWindowUtils.ANIMATION_DIALOG)
                .show();
```
### Some inner Animation
ANIMATION_FADE 
ANIMATION_BOTTOM
ANIMATION_TOP 
ANIMATION_LEFT 
ANIMATION_RIGHT 
ANIMATION_PUSHUP 
ANIMATION_PUSHDOWN
ANIMATION_PUSHUPNOALPHA
ANIMATION_PUSHDOWNNOALPHA
ANIMATION_DIALOG

![enter image description here](http://7xl7dy.com1.z0.glb.clouddn.com/pi.gif)
![enter image description here](http://7xl7dy.com1.z0.glb.clouddn.com/p2.gif)

##About me
- weibo：[@flyou](http://weibo.com/fangjaylong)
- Email：<fangjaylong@gmail.com>

---------

