package com.ruanshuo.news.activity;

import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.ListCommentAdapter;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.CommentBean;
import com.ruanshuo.news.bean._User;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CommentActivity extends BaseActivity {
	private ImageButton imgBtnBack;
	private Button btnSend;
	private Intent intent;
	private ListView lvComment;// 评论列表
	private EditText etComment;
	private ListCommentAdapter adapter;
	private String newsId;
	private Activity activity;
	private TextView tvNoData;

	@Override
	public void initViews() {
		setContentView(R.layout.activity_comment);
		imgBtnBack = (ImageButton) findViewById(R.id.imgbtn_back_comment);
		btnSend = (Button) findViewById(R.id.btn_send_comment_comment);
		lvComment = (ListView) findViewById(R.id.lv_comment);
		etComment = (EditText) findViewById(R.id.et_comment_comment);
		tvNoData = (TextView) findViewById(R.id.tv_no_data_comment);

	}

	@Override
	public void initDatas() {
		activity = this;

		intent = getIntent();
		newsId = intent.getStringExtra("newsId");
		getCount(newsId);

	}

	@Override
	public void initListener() {
		// 返回
		imgBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		// 发表评论
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String commentContent = etComment.getText().toString();
				if (commentContent.isEmpty()) {

					etComment.setHint("请输入内容");

				} else if (BmobUser.getCurrentUser(getApplicationContext(), _User.class) == null) {
					Intent i = new Intent(CommentActivity.this, LoginActivity.class);
					startActivity(i);
				} else {

					_User user = BmobUser.getCurrentUser(getApplicationContext(), _User.class);
					CommentBean commentBean = new CommentBean();
					commentBean.setCommentContent(commentContent);
					if (user.getPic() != null) {
						commentBean.setUpic(user.getPic());
					}

					commentBean.setNewsId(newsId);
					commentBean.setUser(user);
					commentBean.setUname(user.getUsername());
					commentBean.save(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
							etComment.setText("");
							getCount(newsId);

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(getApplicationContext(), "评论失败", Toast.LENGTH_SHORT).show();

						}
					});

				}
			}
		});
	}

	private void getCount(String newsId) {
		BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();

		query.addWhereEqualTo("newsId", newsId);
		query.setLimit(50);
		query.order("-createdAt");
		query.findObjects(getApplicationContext(), new FindListener<CommentBean>() {

			@Override
			public void onSuccess(List<CommentBean> arg0) {
				System.out.println(">>>>>>>>>>>" + arg0.size());
				if (arg0.size() == 0) {
					tvNoData.setVisibility(View.VISIBLE);
				} else {
					adapter = new ListCommentAdapter(activity, getApplicationContext(), arg0);
					lvComment.setAdapter(adapter);
				}

			}

			@Override
			public void onError(int arg0, String arg1) {
				tvNoData.setVisibility(View.VISIBLE);
			}
		});
	}

}
