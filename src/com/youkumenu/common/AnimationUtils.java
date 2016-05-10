package com.youkumenu.common;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class AnimationUtils {
	private static int state=0;
	/**
	 * ת��ȥ����
	 * @param layout ��ǩ
	 */
	public static void rotateOutAnim(RelativeLayout layout){
		rotateAnim(layout, 0f, -180f,0);
	}
	/**
	 * ת��ȥ�������ط���
	 * @param layout ��ǩ
	 * @param startTime ������ʼʱ��
	 */
	public static void rotateOutAnim(RelativeLayout layout,long startTime){
		rotateAnim(layout, 0f, -180f, startTime);
	}
	/**
	 * ת��������
	 * @param layout ��ǩ
	 */
	public static void rotateInAnim(RelativeLayout layout){
		rotateAnim(layout, -180f, 0f,0);
	}
	/**
	 * ת�����������ط���
	 * @param layout
	 * @param startTime
	 */
	public static void rotateInAnim(RelativeLayout layout,long startTime){
		rotateAnim(layout, -180f, 0f,startTime);
	}
	/**
	 * ����ѡװ����
	 * @param layout ��ǩ
	 * @param start ��ʼλ��
	 * @param end ����λ��
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
