package com.example.youkumenu;

import com.youkumenu.common.AnimationUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements OnClickListener {
	private ImageButton btnHome;
	private ImageButton btnMenu;
	private RelativeLayout rlFirst;
	private RelativeLayout rlSecond;
	private RelativeLayout rlThird;
	private boolean thirdFrameState=true;
	private boolean secondFrameState=true;
	private boolean firstFrameState=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlFirst=(RelativeLayout) findViewById(R.id.rlFirst);
        rlSecond=(RelativeLayout) findViewById(R.id.rlSecond);
        rlThird=(RelativeLayout) findViewById(R.id.rlThird);
        btnHome=(ImageButton) findViewById(R.id.btnHome);
        btnMenu=(ImageButton) findViewById(R.id.btnMenu);
        btnHome.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			if (thirdFrameState==false) {
				if (secondFrameState) {
					AnimationUtils.rotateOutAnim(rlSecond);
				}else {
					AnimationUtils.rotateInAnim(rlSecond);
				}
			}else {
				AnimationUtils.rotateOutAnim(rlThird);
				AnimationUtils.rotateOutAnim(rlSecond,200);
				thirdFrameState=!thirdFrameState;
			}
			secondFrameState=!secondFrameState;
			break;
			
		case R.id.btnMenu:
			if (thirdFrameState) {
				AnimationUtils.rotateOutAnim(rlThird);
			}else {
				AnimationUtils.rotateInAnim(rlThird);
			}
			thirdFrameState=!thirdFrameState;
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_MENU) {
			long startTime=0;
			if (firstFrameState) {
				if (secondFrameState) {
					if (thirdFrameState) {
						AnimationUtils.rotateOutAnim(rlThird);
						startTime+=200;
						thirdFrameState=!thirdFrameState;
					}
					AnimationUtils.rotateOutAnim(rlSecond, startTime);
					startTime+=100;
					secondFrameState=!secondFrameState;
				}
				AnimationUtils.rotateOutAnim(rlFirst, startTime);
				firstFrameState=!firstFrameState;
			}else {
				AnimationUtils.rotateInAnim(rlFirst);
				AnimationUtils.rotateInAnim(rlSecond, 200);
				AnimationUtils.rotateInAnim(rlThird, 300);
				firstFrameState=!firstFrameState;
				secondFrameState=!secondFrameState;
				thirdFrameState=!thirdFrameState;
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
