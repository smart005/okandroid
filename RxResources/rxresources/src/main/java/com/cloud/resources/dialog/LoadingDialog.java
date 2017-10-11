package com.cloud.resources.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.cloud.core.Action;
import com.cloud.core.ObjectJudge;
import com.cloud.resources.R;
import com.cloud.resources.dialog.plugs.DialogPlus;
import com.cloud.resources.dialog.plugs.DialogPlusBuilder;
import com.cloud.resources.dialog.plugs.OnDismissListener;
import com.cloud.resources.dialog.plugs.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-24 上午10:31:50
 * @Description: 加载中组件
 * @Modifier:
 * @ModifyContent:
 */
public class LoadingDialog {

    private HashMap<String, DialogPlus> dialogPlusHashMap = new HashMap<String, DialogPlus>();
    private String defDialogId = "249628312";

    public List<DialogPlus> getDialogPlus() {
        List<DialogPlus> lst = new ArrayList<DialogPlus>();
        for (Map.Entry<String, DialogPlus> entry : dialogPlusHashMap.entrySet()) {
            lst.add(entry.getValue());
        }
        return lst;
    }

    public void removeDialogPlus(String removeKey) {
        if (dialogPlusHashMap.containsKey(removeKey)) {
            dialogPlusHashMap.remove(removeKey);
        }
    }

    public void dismiss(DialogPlus dialogPlug) {
        if (dialogPlug == null) {
            return;
        }
        if (dialogPlug.isShowing()) {
            dialogPlug.dismiss();
            if (!TextUtils.isEmpty(dialogPlug.getDialogId())) {
                if (dialogPlusHashMap.containsKey(dialogPlug.getDialogId())) {
                    dialogPlusHashMap.remove(dialogPlug.getDialogId());
                }
            }
        }
    }

    public void dismiss() {
        DialogPlus dialogPlug = getDialogPlug();
        dismiss(dialogPlug);
    }

    public DialogPlus getDialogPlug(String dialogId) {
        if (TextUtils.isEmpty(dialogId)) {
            dialogId = defDialogId;
        }
        DialogPlus dialogPlus = null;
        if (!ObjectJudge.isNullOrEmpty(dialogPlusHashMap) && dialogPlusHashMap.containsKey(dialogId)) {
            dialogPlus = dialogPlusHashMap.get(dialogId);
        }
        return dialogPlus;
    }

    public DialogPlus getDialogPlug() {
        return getDialogPlug(defDialogId);
    }

    public DialogPlus buildDialog(Context context, String dialogId, ViewHolder holder, final Action<DialogPlus> dismissAction) {
        if (TextUtils.isEmpty(dialogId)) {
            dialogId = defDialogId;
        }
        if (dialogPlusHashMap == null) {
            dialogPlusHashMap = new HashMap<String, DialogPlus>();
        }
        DialogPlus dialogPlus = null;
        if (dialogPlusHashMap.containsKey(dialogId)) {
            dialogPlus = dialogPlusHashMap.get(dialogId);
        }
        if (dialogPlus == null) {
            DialogPlusBuilder builder = DialogPlus.newDialog(context)
                    //内容布局
                    .setContentHolder(holder)
                    //点击dialog之外是否消失
                    .setCancelable(false)
                    .setGravity(Gravity.CENTER)
                    //是否根据内容大小自动展开直到充满全屏
                    .setExpanded(false)
                    //内容宽度
                    .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                    //内容高度
                    .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    //弹窗区域之外的背景
                    .setOverlayBackgroundResource(R.color.semi_transparent)
                    //弹窗内容背景
                    .setContentBackgroundResource(R.color.transparent)
                    //overlay背景和屏幕的边距
                    .setOutMostMargin(0, 0, 0, 0)
                    .setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogPlus dialogPlus) {
                            if (dismissAction != null) {
                                dismissAction.execute(dialogPlus);
                            }
                        }
                    });
            dialogPlus = builder.create(dialogId);
            dialogPlusHashMap.put(dialogId, dialogPlus);
        }
        return dialogPlus;
    }

    private DialogPlus showDialog(Context context, String dialogId, String message, final Action<DialogPlus> dismissAction, boolean isShow) {
        ViewHolder holder = new ViewHolder(R.layout.dialog_jr_load);
        DialogPlus dialogPlus = buildDialog(context, dialogId, holder, dismissAction);
        View rootview = dialogPlus.getHolderView().findViewById(R.id.rl_root);
        View rlProgressLogo = dialogPlus.getHolderView().findViewById(R.id.rl_progress_logo);
        RotateAnimation ra = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        ra.setRepeatMode(1);
        ra.setRepeatCount(-1);
        ra.setDuration(2000L);
        ra.setInterpolator(new LinearInterpolator());
        rlProgressLogo.setAnimation(ra);
        TextView tvContent = (TextView) dialogPlus.getHolderView().findViewById(R.id.tv_content);
        tvContent.setText(TextUtils.isEmpty(message) ? "" : message);
        if (isShow) {
            dialogPlus.show();
        }
        return dialogPlus;
    }

    public DialogPlus buildDialog(Context context, String dialogId, String message, final Action<DialogPlus> dismissAction) {
        DialogPlus dialogPlus = showDialog(context, dialogId, message, dismissAction, false);
        return dialogPlus;
    }

    public DialogPlus buildDialog(Context context, String dialogId, String message) {
        return buildDialog(context, dialogId, message, null);
    }

    public void showDialog(Context context, String dialogId, String message, final Action<DialogPlus> dismissAction) {
        DialogPlus dialogPlus = showDialog(context, dialogId, message, dismissAction, true);
        dialogPlus.show();
    }

    public void showDialog(Context context, String message, final Action<DialogPlus> dismissAction) {
        showDialog(context, defDialogId, message, dismissAction);
    }

    public void showDialog(Context context, int resId, final Action<DialogPlus> dismissAction) {
        String message = context.getString(resId);
        showDialog(context, message, dismissAction);
    }

    /**
     * 总进度条
     *
     * @param maxProgress
     * @return
     */
    public void setMaxProgress(DialogPlus dialogPlug, int maxProgress) {
        if (dialogPlug == null || dialogPlug.getHolderView() == null) {
            return;
        }
        DonutProgress dpProgress = (DonutProgress) dialogPlug.getHolderView().findViewById(R.id.dp_progress);
        if (dpProgress == null) {
            return;
        }
        dpProgress.setProgress(0);
        dpProgress.setMax(maxProgress);
    }

    /**
     * 当前进度
     *
     * @param currentProgress
     * @return
     */
    public void setCurrentProgress(DialogPlus dialogPlug, int currentProgress) {
        if (dialogPlug == null || dialogPlug.getHolderView() == null) {
            return;
        }
        DonutProgress dpProgress = (DonutProgress) dialogPlug.getHolderView().findViewById(R.id.dp_progress);
        if (dpProgress == null) {
            return;
        }
        dpProgress.setProgress(currentProgress);
    }

    /**
     * 是否旋转
     *
     * @param isRotate
     * @return
     */
    public void setRotate(DialogPlus dialogPlug, boolean isRotate) {
        if (dialogPlug == null || dialogPlug.getHolderView() == null) {
            return;
        }
        RelativeLayout rlProgressLogo = (RelativeLayout) dialogPlug.getHolderView().findViewById(R.id.rl_progress_logo);
        if (rlProgressLogo == null) {
            return;
        }
        if (isRotate) {
            RotateAnimation ra = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ra.setRepeatMode(Animation.RESTART);
            ra.setRepeatCount(Animation.INFINITE);
            ra.setDuration(2000);
            ra.setInterpolator(new LinearInterpolator());
            rlProgressLogo.setAnimation(ra);
        } else {
            rlProgressLogo.clearAnimation();
        }
    }

    /**
     * 显示文本
     *
     * @param content
     * @return
     */
    public void setContent(DialogPlus dialogPlug, String content) {
        if (dialogPlug == null || dialogPlug.getHolderView() == null || TextUtils.isEmpty(content)) {
            return;
        }
        TextView tvContent = (TextView) dialogPlug.getHolderView().findViewById(R.id.tv_content);
        if (tvContent == null) {
            return;
        }
        tvContent.setText(content);
    }
}
