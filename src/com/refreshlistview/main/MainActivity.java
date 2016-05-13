package com.refreshlistview.main;

import java.util.ArrayList;
import java.util.List;
import com.refreshlistview.main.MyListView.OnRefreshListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MainActivity extends Activity {
	private MyListView myLv;
	private List<String>strList=new ArrayList<String>();
	private MyAdapter myAdapter= new MyAdapter();
	private AddHeaderThread addHeaderThread;
	private class AddHeaderThread extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				Thread.sleep(3000);
				strList.add("这是刷新后的数据");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						myAdapter.notifyDataSetChanged();
						addHeaderThread.interrupt();
						addHeaderThread=null;
						myLv.completeRefresh();
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAdapter();
    }
    /**
     * 初始化适配器
     */
    private void initAdapter() {
		myLv.setAdapter(myAdapter);
	}
    /**
     * 数据初始化
     */
	private void initData() {
		for (int i = 0; i < 30; i++) {
			strList.add("这是ListView中的数据，这是数据"+String.valueOf(i));
		}
	}
	/**
     * 初始化控件
     */
	private void initView() {
		myLv=(MyListView) findViewById(R.id.myLv);
		myLv.SetOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(boolean refreshState) {
				if (refreshState) {
					if (addHeaderThread==null) {
						addHeaderThread=new AddHeaderThread();
						addHeaderThread.start();
					}
				}
			}
		});
	}
	/**
	 * ListView适配器
	 * @author donghao
	 *
	 */
	private class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return strList.size();
		}

		@Override
		public Object getItem(int position) {
			return strList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tView=new TextView(MainActivity.this);
			tView.setText(strList.get(position));
			tView.setTextSize(18f);
			return tView;
		}
		
	}
}
