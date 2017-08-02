package com.bmob.fast;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.v3.Bmob;

/**
 * @ClassName: BaseActivity
 * @Description: TODO
 * @author yu
 * @date 2017-5-20 9:55:34
 */
public class BaseActivity extends Activity {

	private String Bmob_AppId = "71f32c3647fa311b42b9bcb5574225e8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, Bmob_AppId);
	}

	Toast mToast;

	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
}
