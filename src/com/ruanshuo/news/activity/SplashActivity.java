package com.ruanshuo.news.activity;

import com.ruanshuo.news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import cn.bmob.v3.Bmob;

public class SplashActivity extends Activity {
	private TextView tvTitle, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Bmob.initialize(this, "735de513622b7eb5cc90d61c165d7b60");
		setContentView(R.layout.activity_splash);
		initUI();
		setAnimation();

	}

	private void setAnimation() {
		ScaleAnimation scaleAnimation = new ScaleAnimation(3f, 1f, 3f, 1f);
		scaleAnimation.setDuration(1000);
		tvTitle.startAnimation(scaleAnimation);
		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				final RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation.setDuration(1000);
				tv1.startAnimation(rotateAnimation);
				tv2.startAnimation(rotateAnimation);
				tv3.startAnimation(rotateAnimation);
				tv4.startAnimation(rotateAnimation);
				tv5.startAnimation(rotateAnimation);
				tv6.startAnimation(rotateAnimation);
				tv7.startAnimation(rotateAnimation);
				tv8.startAnimation(rotateAnimation);
				rotateAnimation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(intent);
						finish();

					}
				});
			}
		});
	}

	private void initUI() {
		tvTitle = (TextView) findViewById(R.id.textViewTitle);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);

	}

}
