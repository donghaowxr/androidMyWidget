package com.togglebutton.main;

import com.togglebutton.main.MyButton.OnSwitchStateUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity {
	private MyButton btnToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnToggle=(MyButton) findViewById(R.id.btnToggle);
        btnToggle.setOnSwitchStateUpdateListener(new OnSwitchStateUpdateListener() {
			@Override
			public void onStateUpdate(boolean state) {
				Toast.makeText(MainActivity.this, String.valueOf(state), Toast.LENGTH_SHORT).show();
			}
		});
    }
}
