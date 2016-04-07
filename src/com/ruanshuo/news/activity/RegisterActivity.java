package com.ruanshuo.news.activity;

import com.ruanshuo.news.R;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean._User;

import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
	private ImageButton imgBtn_back;// 返回按钮
	private Button btn_register;// 注册按钮
	private EditText et_uname, et_pwd, et_rpwd, et_mail;// 输入框
	private ProgressDialog dialog;

	/*
	 * 初始化View
	 */
	@Override
	public void initViews() {
		// 设置布局文件
		setContentView(R.layout.activity_register);
		/*
		 * findView
		 */
		imgBtn_back = (ImageButton) findViewById(R.id.imgbtn_back_register);
		btn_register = (Button) findViewById(R.id.btn_register);
		et_uname = (EditText) findViewById(R.id.et_name_register);
		et_pwd = (EditText) findViewById(R.id.et_pass_register);
		et_rpwd = (EditText) findViewById(R.id.et_repass_register);
		et_mail = (EditText) findViewById(R.id.et_mail_register);
	}

	/*
	 * 初始化数据
	 */
	@Override
	public void initDatas() {

	}

	/*
	 * 初始化监听器
	 */
	@Override
	public void initListener() {
		// 返回按钮监听事件
		imgBtn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 注册监听事件
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 获取编辑框的内容
				String uname = et_uname.getText().toString();
				String pwd = et_pwd.getText().toString();
				String rpwd = et_rpwd.getText().toString();
				String mail = et_mail.getText().toString();
				// 判断输入框内容是否为空
				if (uname.isEmpty() || pwd.isEmpty() || rpwd.isEmpty() || mail.isEmpty()) {

					Toast.makeText(getApplicationContext(), "请完善输入", Toast.LENGTH_SHORT).show();
				} else if (!pwd.equals(rpwd)) {

					Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
					et_pwd.setText("");
					et_rpwd.setText("");
				} else {

					dialog = new ProgressDialog(RegisterActivity.this, R.style.ThemeTransparent);
					dialog.setCanceledOnTouchOutside(true);
					dialog.setCancelable(true);
					dialog.setMessage("请稍候");
					dialog.show();
					_User user = new _User();
					user.setUsername(uname);
					user.setPassword(pwd);
					user.setEmail(mail);
					// 注册事件
					user.signUp(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), "注册成功，请登录邮箱激活账号", Toast.LENGTH_LONG).show();

							et_uname.setText("");
							et_pwd.setText("");
							et_rpwd.setText("");
							et_mail.setText("");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), "对不起，注册失败：\n" + arg1, Toast.LENGTH_LONG).show();
							et_uname.setText("");
							et_pwd.setText("");
							et_rpwd.setText("");
							et_mail.setText("");
						}
					});
				}

			}
		});

	}

}
