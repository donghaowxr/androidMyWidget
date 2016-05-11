package com.viewpagerflash.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;


public class MainActivity extends Activity implements OnPageChangeListener {
	private ViewPager viewPager;
	private TextView tvShow;
	private LinearLayout llParent;
	private List<ImageView>imageViewList;
	private List<String>textList;
	private FlashThread flashThread;
	private class FlashThread extends Thread{
		private boolean state=true;
		public void stopThread(){
			state=false;
		}
		@Override
		public void run() {
			super.run();
			while (state) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
					}
				});
			}
		}
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
        initData();
        initAdapter();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if (flashThread!=null) {
			flashThread.stopThread();
			flashThread.interrupt();
			flashThread=null;
		}
    }
    /**
     * ���ݳ�ʼ��
     */
    private void initData() {
		int imageResIds[]={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
		ImageView imageView;
		for (int i = 0; i < imageResIds.length; i++) {
			imageView=new ImageView(this);
			imageView.setBackgroundResource(imageResIds[i]);
			imageViewList.add(imageView);
			View pointView=new View(this);
			pointView.setBackgroundResource(R.drawable.point_selector);
			LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(10, 10);
			layoutParams.topMargin=8;
			if (i!=0) {
				layoutParams.leftMargin=8;
				pointView.setEnabled(false);
			}
			llParent.addView(pointView, layoutParams);
		}
		tvShow.setText(textList.get(0));
	}
	/**
     * ��ʼ��������
     */
    private void initAdapter() {
		viewPager.setAdapter(new myAdapter());
		viewPager.setOnPageChangeListener(this);
		/**
		 * ���䷽ʽ����ViewPage�������ٶ�
		 */
		try {
			Field mScroller=ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller=new FixedSpeedScroller(viewPager.getContext(), new AccelerateDecelerateInterpolator());
			mScroller.set(viewPager, scroller);
			scroller.setmDuration(1000);
		} catch (Exception e) {
		}
		int firstPosition=50000-50000%imageViewList.size();
		viewPager.setCurrentItem(firstPosition);
		if (flashThread==null) {
			flashThread=new FlashThread();
			flashThread.start();
		}
	}
	/**
     * ��ʼ���ؼ�
     */
	private void initPager() {
		viewPager=(ViewPager) findViewById(R.id.viewPager);
		tvShow=(TextView) findViewById(R.id.tvShow);
		llParent=(LinearLayout) findViewById(R.id.llParent);
		imageViewList=new ArrayList<ImageView>();
		textList=new ArrayList<String>(Arrays.asList("���ǵ�һ��ҳ��","���ǵڶ���ҳ��","���ǵ�����ҳ��","���ǵ��ĸ�ҳ��","���ǵ����ҳ��"));
	}
	/**
	 * ViewPager������
	 * @author donghao
	 *
	 */
	private class myAdapter extends PagerAdapter{
		/**
		 * ��ȡ��Ԫ�صĸ���
		 */
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		/**
		 * ��������
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		/**
		 * Ԫ�����ٹ���
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			int newPosition=position%imageViewList.size();
			container.removeView(imageViewList.get(newPosition));
		}
		/**
		 * ��ʾ��ǰԪ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int newPosition=position%imageViewList.size();
			container.addView(imageViewList.get(newPosition));
			return imageViewList.get(newPosition);
		}
	}
	/**
	 * ����ViewPage�����ٶȿ�����
	 */
	public class FixedSpeedScroller extends Scroller{
		private int mDuration = 1000;
		public FixedSpeedScroller(Context context) {
	        super(context);
	    }
	 
	    public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }
	    
		public FixedSpeedScroller(Context context, Interpolator interpolator,
				boolean flywheel) {
			super(context, interpolator, flywheel);
		}
		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy,mDuration);
		}
		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			// TODO Auto-generated method stub
			super.startScroll(startX, startY, dx, dy, mDuration);
		}
		public void setmDuration(int time){
			mDuration=time;
		}
		public int getmDuration(){
			return mDuration;
		}
	}
	/**
	 * viewPager״̬�ı�ʱ����
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}
	/**
	 * viewPager������ʱ�����
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	/**
	 * viewPagerҳ����ת��ɺ����
	 */
	@Override
	public void onPageSelected(int arg0) {
		int newPosition=arg0%imageViewList.size();
		for (int i = 0; i < llParent.getChildCount(); i++) {
			llParent.getChildAt(i).setEnabled(i==newPosition);
		}
		tvShow.setText(textList.get(newPosition));
	}
}
