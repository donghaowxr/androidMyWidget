package com.refreshlistview.main;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint({ "SimpleDateFormat", "ClickableViewAccessibility" }) public class MyListView extends ListView {
	private float downY;
	private float moveY;
	private View headerView;
	private View footerView;
	private ImageView ivState;
	private ProgressBar pgRing;
	private TextView tvState;
	private TextView tvTime;
	private float movePadding;
	private float oldMovePadding;
	private int measureHeight;
	private int footMeasureHeight;
	private int state;
	private boolean paddingState=false;
	private boolean footerState=false;
	private int pullState=3;
	private int refreshingState=4;

	private RotateAnimation rotatePullAnimation;
	private RotateAnimation rotatePushAnimation;
	private OnRefreshListener onRefreshListener;
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}
	public MyListView(Context context) {
		this(context, null);
	}
	/**
	 * ��ʼ��
	 */
	private void init() {
		state=pullState;
		initHeader();
		initFooter();
		initAnimation();
	}
	/**
	 * ��ʼ���Ų�����
	 */
	private void initFooter() {
		footerView=View.inflate(getContext(), R.layout.item_footer, null);
		addFooterView(footerView);
		footerView.measure(0, 0);
		footMeasureHeight=footerView.getMeasuredHeight();
		footerView.setPadding(0, -footMeasureHeight, 0, 0);
		this.setOnScrollListener(onScrollListener);
	}

	/**
	 * ��ʼ������
	 */
	private void initAnimation() {
		rotatePullAnimation=new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		setAnimation(rotatePullAnimation);
		rotatePushAnimation=new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		setAnimation(rotatePushAnimation);
	}
	
	private void setAnimation(RotateAnimation rotateAnimation){
		rotatePullAnimation.setDuration(300);
		rotatePullAnimation.setFillAfter(true);
	}

	/**
	 * ��ʼ��ͷ��
	 */
	private void initHeader() {
		headerView=View.inflate(getContext(), R.layout.item_header, null);
		addHeaderView(headerView);
		ivState=(ImageView) headerView.findViewById(R.id.ivState);
		pgRing=(ProgressBar) headerView.findViewById(R.id.pg_ring);
		tvState=(TextView) headerView.findViewById(R.id.tvState);
		tvTime=(TextView) headerView.findViewById(R.id.tvTime);
		headerView.measure(0, 0);//�ֶ�����ͷ��
		measureHeight=headerView.getMeasuredHeight();
		headerView.setPadding(0, -measureHeight, 0, 0);
	}
	
	/**
	 * ������������������������ڿ���״̬����ʾ�������һ������+1���������
	 */
	private OnScrollListener onScrollListener=new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			System.out.println(getLastVisiblePosition()+":"+getCount());
			if (!footerState) {
				if (scrollState==SCROLL_STATE_IDLE&&getLastVisiblePosition()>=(getCount()-1)) {
					footerView.setPadding(0, 0, 0, 0);
					footerState=true;
					onRefreshListener.onRefresh(footerState);
				}
			}
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY=ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (state==pullState) {
				moveY=ev.getY();
				movePadding=moveY-downY;
				if (movePadding<oldMovePadding) {
					if (paddingState) {
						ivState.startAnimation(rotatePushAnimation);
						paddingState=false;
					}
				}else {
					if (movePadding>0&&getFirstVisiblePosition()==0) { //����ָ�ƶ��������0���ҵ�ǰ��ʾ��һ��Ԫ�ص�����Ϊ0ʱ��ʾͷ��
						if (movePadding>=measureHeight) {
							if (!paddingState) {
								ivState.startAnimation(rotatePullAnimation);
								paddingState=true;
							}
						}
						headerView.setPadding(0, -measureHeight+(int)movePadding, 0, 0);
					}
				}
				oldMovePadding=movePadding;
			}
			break;
		case MotionEvent.ACTION_UP:
			ivState.clearAnimation();
			if (paddingState) {
				ivState.setVisibility(View.INVISIBLE);
				pgRing.setVisibility(View.VISIBLE);
				tvState.setText("ˢ����...");
				state=refreshingState;
				headerView.setPadding(0, 0, 0, 0);
				onRefreshListener.onRefresh(paddingState);
			}else {
				this.scrollTo(0, 0);
				headerView.setPadding(0, -measureHeight, 0, 0);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	/**
	 * ���ˢ�·���
	 */
	public void completeRefresh(){
		SimpleDateFormat sDateFormat =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");       
		String date = sDateFormat.format(new    java.util.Date()); 
		paddingState=false;
		footerState=false;
		ivState.setVisibility(View.VISIBLE);
		pgRing.setVisibility(View.INVISIBLE);
		tvState.setText("��ʼˢ��...");
		tvTime.setText("������ʱ��:"+date);
		state=pullState;
		headerView.setPadding(0, -measureHeight, 0, 0);
		footerView.setPadding(0, -footMeasureHeight, 0, 0);
	}
	/**
	 * ˢ����״̬�ص��ӿ�
	 * @author donghao
	 *
	 */
	public interface OnRefreshListener{
		void onRefresh(boolean refreshState);
	}
	/**
	 * ˢ����״̬�ص�����
	 * @param onRefreshListener �ӿڶ���
	 */
	public void SetOnRefreshListener(OnRefreshListener onRefreshListener){
		this.onRefreshListener=onRefreshListener;
	}
}
