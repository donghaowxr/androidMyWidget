package com.slipmenu.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

@SuppressLint("ClickableViewAccessibility") public class SlipMenu extends ViewGroup {
	private float downX;
	private float downY;
	private float moveX;
	private float moveY;
	private int newScrollPosition;
	private float scrollX;
	private float scrollY;
	private int menuState;
	private int MENU_SHOW=0;
	private int MENU_NONE=1;
	private Scroller scroller;
	private OnSlipMenuState onSlipMenuState;
	public SlipMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	private void init() {
		menuState=MENU_NONE;
		scroller=new Scroller(getContext());
	}
	public SlipMenu(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}
	public SlipMenu(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		View leftMenu=getChildAt(0);
		View mainMenu=getChildAt(1);
		leftMenu.measure(leftMenu.getLayoutParams().width, heightMeasureSpec);//宽度为设置的宽度，高度为默认
		mainMenu.measure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		getChildAt(0).layout(-getChildAt(0).getLayoutParams().width, 0, 0, getChildAt(0).getMeasuredHeight());
		getChildAt(1).layout(arg1, arg2, arg3, arg4);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX=event.getX();
			downY=event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			moveX=event.getX();
			moveY=event.getY();
			scrollX=downX-moveX;
			scrollY=downY-moveY;
			if (Math.abs(scrollX)>Math.abs(scrollY)&&Math.abs(scrollX)>5) {
				newScrollPosition=getScrollX()+(int)scrollX;
				if (newScrollPosition<-getChildAt(0).getLayoutParams().width) {
					scrollTo(-getChildAt(0).getLayoutParams().width, 0);
				}else if (newScrollPosition>0) {
					scrollTo(0, 0);
				}else {
					scrollTo(newScrollPosition, 0);
				}
			}
			downX=moveX;
			downY=moveY;
			break;
		case MotionEvent.ACTION_UP:
			int halfWidth=getChildAt(0).getLayoutParams().width/2;
			if (Math.abs(getScrollX())>halfWidth) { //如果当前控件坐标绝对值大于侧滑页面宽度的一半则显示侧滑栏
				menuState=MENU_SHOW;
				updateMenu();
			}else {
				menuState=MENU_NONE;
				updateMenu();
			}
			scrollX=0;
			scrollY=0;
			break;
		}
		return true;
	}
	private void updateMenu() {
		int durtion=getChildAt(0).getLayoutParams().width;
		if (menuState==MENU_SHOW) {
			scrollTo(-getChildAt(0).getLayoutParams().width, 0);
			scroller.startScroll(getScrollX(), 0, -getChildAt(0).getLayoutParams().width-getScrollX(), 0, durtion);
			onSlipMenuState.onSlipMenuState(true);
		}else {;
			scroller.startScroll(getScrollX(), 0, 0-getScrollX(), 0, durtion);
			onSlipMenuState.onSlipMenuState(false);
		}
		invalidate();
		
	}
	
	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int currX=scroller.getCurrX();
			scrollTo(currX, 0);
			invalidate();
		}
	}
	/**
	 * 切换menu状态
	 */
	public void toggleMenu(){
		if (menuState==MENU_SHOW) {
			menuState=MENU_NONE;
			updateMenu();
		}else {
			menuState=MENU_SHOW;
			updateMenu();
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_MOVE) {
			return true;
		}else {
			return super.onInterceptTouchEvent(ev);
		}
	}
	/**
	 * 定义滑动状态接口
	 * @author donghao
	 *
	 */
	public interface OnSlipMenuState{
		void onSlipMenuState(boolean state);
	}
	public void setOnSlipMenuState(OnSlipMenuState onSlipMenuState){
		this.onSlipMenuState=onSlipMenuState;
	}
}
