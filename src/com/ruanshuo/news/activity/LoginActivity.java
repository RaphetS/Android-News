package com.ruanshuo.news.activity;

import com.ruanshuo.news.R;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.common.AppActivityManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
	private ProgressDialog dialog;
	private ImageButton imgBtnBack;
	private Button btn_to_register, btn_login;
	private EditText et_uname, et_pwd;

	/*
	 * 初始化视图
	 */
	@Override
	public void initViews() {
		setContentView(R.layout.activity_login);
		imgBtnBack = (ImageButton) findViewById(R.id.imgbtn_back_login);
		btn_to_register = (Button) findViewById(R.id.btn_to_register);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_uname = (EditText) findViewById(R.id.et_name);
		et_pwd = (EditText) findViewById(R.id.et_pass);
	}

	@Override
	public void initDatas() {
		
	}

	@Override
	public void initListener() {
		// 返回
		imgBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getIntent().getStringExtra("msg").equals("logout")) {
					Log.e("xxxx", getIntent().getStringExtra("msg"));
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Log.e("yyyyy", getIntent().getStringExtra("msg"));
					finish();
				}

			}
		});
		// 进入到注册界面
		btn_to_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);

			}
		});
		// 登陆监听事件
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uname = et_uname.getText().toString();
				String pwd = et_pwd.getText().toString();
				if (uname.isEmpty() || pwd.isEmpty()) {
					Toast.makeText(getApplicationContext(), "请输入账号或密码", Toast.LENGTH_SHORT).show();
				} else {
					dialog = new ProgressDialog(LoginActivity.this, R.style.ThemeTransparent);
					dialog.setCanceledOnTouchOutside(true);
					dialog.setCancelable(true);
					dialog.setMessage("正在登陆");
					dialog.show();
					final _User user = new _User();
					user.setUsername(uname);
					user.setPassword(pwd);
					user.login(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							if (user.getEmailVerified()) {
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								startActivity(intent);
								finish();
								AppActivityManager.getInstance().exit();
							} else {
								dialog.dismiss();
								Toast.makeText(getApplicationContext(), "邮箱未验证，请先进入邮箱进行验证", Toast.LENGTH_LONG).show();
							}

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), "登陆失败" + arg1, Toast.LENGTH_LONG).show();

						}
					});
				}

			}
		});

	}

}
