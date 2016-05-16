package com.slipmenu.main;

import com.slipmenu.main.SlipMenu.OnSlipMenuState;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {
	private SlipMenu slipMenu;
	private ImageButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slipMenu=(SlipMenu) findViewById(R.id.slipMenu);
        toggleButton=(ImageButton) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				slipMenu.toggleMenu();
			}
		});
        slipMenu.setOnSlipMenuState(new OnSlipMenuState() {
			@Override
			public void onSlipMenuState(boolean state) {
				Toast.makeText(MainActivity.this, String.valueOf(state), Toast.LENGTH_SHORT).show();
			}
		});
    }
}
