package com.cloud.resources.beans;

/**
 * @Author lijinghuan
 * @Email: ljh0576123@163.com
 * @CreateTime:2016/4/11 0:08
 * @Description: 滚轮初始属性
 * @Modifier:
 * @ModifyContent:
 */
public class WheelInitProperties {

    /**
     * 视图背景
     */
    private int viewBackground = 0;

    /**
     * 选中项背景
     */
    private int selectedItemBackground = 0;

    /**
     * 渐变背景色（start->end,颜色越多越精确）
     */
    private int[] gradientBackgroundColors = new int[0];


    /**
     * @return 获取视图背景
     */
    public int getViewBackground() {
        return viewBackground;
    }

    /**
     * 设置视图背景
     *
     * @param viewBackground
     */
    public void setViewBackground(int viewBackground) {
        this.viewBackground = viewBackground;
    }

    /**
     * @return 获取选中项背景
     */
    public int getSelectedItemBackground() {
        return selectedItemBackground;
    }

    /**
     * 设置选中项背景
     *
     * @param selectedItemBackground
     */
    public void setSelectedItemBackground(int selectedItemBackground) {
        this.selectedItemBackground = selectedItemBackground;
    }

    /**
     * @return 获取渐变背景色(start->end颜色越多越精确)
     */
    public int[] getGradientBackgroundColors() {
        if (gradientBackgroundColors == null) {
            return new int[0];
        } else {
            return gradientBackgroundColors;
        }
    }

    /**
     * 设置渐变背景色(start->end颜色越多越精确)
     *
     * @param gradientBackgroundColors
     */
    public void setGradientBackgroundColors(int[] gradientBackgroundColors) {
        this.gradientBackgroundColors = gradientBackgroundColors;
    }
}
