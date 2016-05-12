package com.myspinner.main;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
	private EditText etText;
	private ImageButton btnShow;
	private ListView listView;
	private RelativeLayout rlView;
	private PopupWindow popupWindow;
	private List<String>dateList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnShow.setOnClickListener(this);
        rlView.setOnClickListener(this);
    }
	private void initView() {
		etText=(EditText) findViewById(R.id.etText);
		btnShow=(ImageButton) findViewById(R.id.btnShow);
		rlView=(RelativeLayout) findViewById(R.id.rlView);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShow:
			listView=new ListView(this);
			initData();
			initAdapter();
			popupWindow=new PopupWindow(listView, etText.getWidth(), 700);
			popupWindow.showAsDropDown(etText, 0, 0);
			popupWindow.setOutsideTouchable(true);
			
			break;
		case R.id.rlView:
			if (popupWindow.isShowing()) {
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
				popupWindow.dismiss();
			}
			break;
		default:
			break;
		}
	}
	private void initData() {
		for (int i = 0; i < 10000; i++) {
			String textString="ÁªÏµÈË"+String.valueOf(i);
			dateList.add(textString);
		}
	}
	private void initAdapter() {
		listView.setAdapter(new MyAdapter());
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return dateList.size();
		}

		@Override
		public Object getItem(int position) {
			return dateList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView==null) {
				view=View.inflate(parent.getContext(), R.layout.item_contact, null);
			}else {
				view=convertView;
			}
			TextView tvText=(TextView) view.findViewById(R.id.tvText);
			ImageButton btnDelete=(ImageButton) view.findViewById(R.id.btnDelete);
			tvText.setText(dateList.get(position));
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					etText.setText(dateList.get(position));
				}
			});
			btnDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dateList.remove(position);
					if (dateList.size()==0) {
						popupWindow.dismiss();
					}
					notifyDataSetChanged();
				}
			});
			return view;
		}
		
	}
}
