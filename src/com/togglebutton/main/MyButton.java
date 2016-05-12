package com.togglebutton.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility") public class MyButton extends View {
	private Bitmap backBitmap;
	private Bitmap slidBitmap;
	private Paint paint;
	private boolean btnState;
	private boolean touchState;
	private OnSwitchStateUpdateListener onSwitchStateUpdateListener;
	public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
	}
	private void init() {
		touchState=false;
		paint=new Paint();
	}
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		String nameSpace="http://schemas.android.com/apk/res/com.togglebutton.main";
		int backResourceId=attrs.getAttributeResourceValue(nameSpace, "backResource", -1);
		int sidResourceId=attrs.getAttributeResourceValue(nameSpace, "slidResource", -1);
		boolean userState=attrs.getAttributeBooleanValue(nameSpace, "toggleState", false);
		setBackBitmapSource(backResourceId);
		setSlidBitmapSource(sidResourceId);
		btnState=userState;
	}
	public MyButton(Context context) {
		this(context, null);
	}
	
	/**
	 * ����������ָ���ؼ����
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(backBitmap.getWidth(), backBitmap.getHeight());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//���Ʊ���ͼƬ
		canvas.drawBitmap(backBitmap, 0, 0, paint);
		//����ǰ��ͼƬ
		if (btnState) {
			float left=backBitmap.getWidth()-slidBitmap.getWidth();
			canvas.drawBitmap(slidBitmap, left, 0, paint);
		}else {
			canvas.drawBitmap(slidBitmap, 0, 0, paint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchState=true;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (touchState) {
				btnState=!btnState;
				if (onSwitchStateUpdateListener!=null) {
					onSwitchStateUpdateListener.onStateUpdate(btnState);
				}
				touchState=!touchState;
				invalidate();
			}
			break;
		default:
			break;
		}
		return true;
	}
	
	/**
	 * �����ȡ�ؼ�״̬�Ľӿ�
	 * @author donghao
	 *
	 */
	public interface OnSwitchStateUpdateListener{
		void onStateUpdate(boolean state);
	}
	/**
	 * ���û�ȡ��ť״̬�ص�����
	 * @param onSwitchStateUpdateListener �ӿڶ���
	 */
	public void setOnSwitchStateUpdateListener(
			OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
		this.onSwitchStateUpdateListener=onSwitchStateUpdateListener;
	}
	/**
	 * ���ð�ť����ͼƬ
	 * @param switchBackground ����ͼƬ��Դid
	 */
	public void setBackBitmapSource(int switchBackground) {
		backBitmap=getBitmapResource(switchBackground);
	}
	/**
	 * ���ð�ťǰ��ͼƬ/����ͼƬ
	 * @param slideButton ǰ��ͼƬ��Դid
	 */
	public void setSlidBitmapSource(int slideButton) {
		slidBitmap=getBitmapResource(slideButton);
	}
	/**
	 * ��ȡͼƬ��Դ
	 * @param id ͼƬ��Դid
	 * @return ͼƬ��Դ
	 */
	private Bitmap getBitmapResource(int id){
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), id);
		return bitmap;
	}
	/**
	 * ���ð�ť��״̬
	 * @param b ����ֵ״̬
	 */
	public void setButtonState(boolean b) {
		btnState=b;
	}
}
