package com.youkumenu.common;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class AnimationUtils {
	private static int state=0;
	/**
	 * 转出去动画
	 * @param layout 标签
	 */
	public static void rotateOutAnim(RelativeLayout layout){
		rotateAnim(layout, 0f, -180f,0);
	}
	/**
	 * 转出去动画重载方法
	 * @param layout 标签
	 * @param startTime 动画开始时间
	 */
	public static void rotateOutAnim(RelativeLayout layout,long startTime){
		rotateAnim(layout, 0f, -180f, startTime);
	}
	/**
	 * 转进来动画
	 * @param layout 标签
	 */
	public static void rotateInAnim(RelativeLayout layout){
		rotateAnim(layout, -180f, 0f,0);
	}
	/**
	 * 转进来动画重载方法
	 * @param layout
	 * @param startTime
	 */
	public static void rotateInAnim(RelativeLayout layout,long startTime){
		rotateAnim(layout, -180f, 0f,startTime);
	}
	/**
	 * 启动选装动画
	 * @param layout 标签
	 * @param start 开始位置
	 * @param end 结束位置
	 */
	private static void rotateAnim(RelativeLayout layout,float start,float end,long startTime){
		if (state==0) {
			RotateAnimation ra=new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
			ra.setDuration(500);
			ra.setFillAfter(true);
			ra.setStartOffset(startTime);
			layout.startAnimation(ra);
		}
	}
	
	@SuppressWarnings("unused")
	private class MyAnimationListener implements AnimationListener{
		@Override
		public void onAnimationStart(Animation animation) {
			state++;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			state--;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
	}
}
