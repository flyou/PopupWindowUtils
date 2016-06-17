package com.flyou.popupwindowutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fzl on 2016/6/16.
 */
public class PopupWindowUtils {
    public static final int ANIMATION_FADE = R.style.Animation_Fade;
    public static final int ANIMATION_BOTTOM = R.style.Animation_Bottom;
    public static final int ANIMATION_TOP = R.style.Animation_Top;
    public static final int ANIMATION_LEFT = R.style.Animation_left;
    public static final int ANIMATION_RIGHT = R.style.Animation_right;
    public static final int ANIMATION_PUSHUP = R.style.Animation_pushUp;
    public static final int ANIMATION_PUSHDOWN = R.style.Animation_pushDown;
    public static final int ANIMATION_PUSHUPNOALPHA = R.style.Animation_pushUpNoAlpha;
    public static final int ANIMATION_PUSHDOWNNOALPHA = R.style.Animation_pushDownNoAlpha;
    public static final int ANIMATION_DIALOG = R.style.Animation_Dialog;
    private int mAnimStyle = R.style.Animation_Dialog;
    private PopupWindow mPopupWindow;
    private View mParentView;
    private static int VIEWWIDTH = 600;
    private static int VIEWHEIGHT = 900;
    private static boolean FOCUSEABLE = true;
    private static int SHOWGRAVITY = Gravity.CENTER;
    private static int OFFSETX = 0;
    private static int OFFSETY = 0;
    private Activity ACTIVITY = null;
    private static int PARENTVIEWID = 0;
    private static View ATTACHVIEW = null;
    private static View ANCHOR = null;
    private SetonPopWindowClickListenter mPopWindowClickListenter = null;
    private SetonPopWindowItemViewClickListenter mPopWindowItemViewClickListenter = null;
    private int[] mItemViewId = null;


    private static class SingletonHolder {
        private static final PopupWindowUtils INSTANCE = new PopupWindowUtils();
    }

    private PopupWindowUtils() {
    }

    public static final PopupWindowUtils getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public void show() {
        mParentView = View.inflate(ACTIVITY, PARENTVIEWID, null);
        if (mParentView == null) {
            return;
        }
        mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWindowClickListenter != null)
                    mPopWindowClickListenter.onPopWindowClickListener();
            }
        });

        if (mItemViewId !=null) {
            for (final int id : mItemViewId) {
                mParentView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPopWindowItemViewClickListenter != null)
                            mPopWindowItemViewClickListenter.onPopWindowItemViewClickListener(id);
                    }
                });
            }

        }
        mPopupWindow = new PopupWindow(mParentView, VIEWWIDTH, VIEWHEIGHT, FOCUSEABLE);
        //设置动画方式
        mPopupWindow.setAnimationStyle(mAnimStyle);

        // 这里是位置显示方式
        if (ANCHOR != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mPopupWindow.showAsDropDown(ANCHOR, OFFSETX, OFFSETY, SHOWGRAVITY);
            } else {
                mPopupWindow.showAsDropDown(ANCHOR);
            }
        } else {
            mPopupWindow.showAtLocation(ATTACHVIEW, SHOWGRAVITY, OFFSETX, OFFSETY);
        }

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        // 设置背景颜色变暗
        setBackBrightness(0.7f, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackBrightness(1.0f, 500);
            }
        });
    }

    public PopupWindowUtils wiewSize(int wiewWidth,int wiewHeight) {
        this.VIEWWIDTH = wiewWidth;
        this.VIEWHEIGHT = wiewHeight;
        return getInstance();
    }

    public PopupWindowUtils focuseAble(boolean focuseAble) {
        this.FOCUSEABLE = focuseAble;
        return getInstance();
    }

    public PopupWindowUtils location(int showGravity, int offsetX, int offsetY) {
        this.SHOWGRAVITY = showGravity;
        this.OFFSETX = offsetX;
        this.OFFSETY = offsetY;
        return getInstance();
    }

    public PopupWindowUtils location(int showGravity) {
        this.SHOWGRAVITY = showGravity;

        return getInstance();
    }

    public PopupWindowUtils asDropDown(View anchor, int offsetX, int offsetY, int gravity) {
        this.ANCHOR = anchor;
        this.OFFSETX = offsetX;
        this.OFFSETY = offsetY;
        this.SHOWGRAVITY = gravity;
        return getInstance();
    }

    public PopupWindowUtils asDropDown(View anchor, int gravity) {
        this.ANCHOR = anchor;

        this.SHOWGRAVITY = gravity;
        return getInstance();
    }

    public PopupWindowUtils asDropDown(View anchor) {
        this.ANCHOR = anchor;

        return getInstance();
    }

    /**
     * 设置 popWindow的动画，可以使用内部定义的style也可以传入自己的styleID
     *
     * @param AnimationStyle 动画id需要在自己的style文件中声明
     * @return
     */

    public PopupWindowUtils Animation(int AnimationStyle) {

        this.mAnimStyle = AnimationStyle;
        return getInstance();
    }

    /**
     * 对popWindow点击事件的监听
     *
     * @param listener
     * @return
     */
    public PopupWindowUtils setPopupWindowClickListener(SetonPopWindowClickListenter listener) {

        this.mPopWindowClickListenter = listener;

        return getInstance();
    }

    /**
     * 输入子View id 设置对popWindow内部view的监听
     *
     * @param itemViewId
     * @param listener
     * @return
     */
    public PopupWindowUtils setPopupWindowItemViewClickListener( SetonPopWindowItemViewClickListenter listener,int... itemViewId) {
        this.mItemViewId = itemViewId;
        this.mPopWindowItemViewClickListenter = listener;

        return getInstance();
    }

    /**
     * 绑定视图，需要首先调用
     *
     * @param activity
     * @param parentViewId popWindow展示的视图id
     * @param attachView   依附的view
     * @return
     */
    public PopupWindowUtils attachActivity(Activity activity, int parentViewId, View attachView) {
        this.ACTIVITY = activity;
        this.PARENTVIEWID = parentViewId;
        this.ATTACHVIEW = attachView;
        return getInstance();
    }


    public interface SetonPopWindowClickListenter {
        void onPopWindowClickListener();
    }

    public interface SetonPopWindowItemViewClickListenter {
        void onPopWindowItemViewClickListener(int id);
    }

    /**
     * 设置 背景亮度
     *
     * @param percent 亮度
     * @param delay   延迟时间
     */
    private void setBackBrightness(final float percent, int delay) {

        TimerTask task = new TimerTask() {
            public void run() {
                ACTIVITY.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WindowManager.LayoutParams lp = ACTIVITY.getWindow().getAttributes();
                        lp.alpha = percent;
                        ACTIVITY.getWindow().setAttributes(lp);
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, delay);

    }

    /**
     * 设置popWindow消失
     */
    public void dissMissPopWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }

    }

    public  int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
