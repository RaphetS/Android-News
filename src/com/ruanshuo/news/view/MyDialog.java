package com.ruanshuo.news.view;

import com.ruanshuo.news.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyDialog extends Dialog {
	public TextView tv;
	private Button btn;
	private Context ctx;
	private int theme;
	public MyDialog(Context context) {
		super(context);
		ctx=context;
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
		ctx=context;
		this.theme=theme;
	}

	public MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_dailog);
		btn=(Button) findViewById(R.id.btn_ok_dialog);
		tv=(TextView) findViewById(R.id.tv_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyDialog.this.dismiss();
				
			}
		});
	}

}
