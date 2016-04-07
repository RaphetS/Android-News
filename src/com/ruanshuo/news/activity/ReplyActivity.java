package com.ruanshuo.news.activity;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.ListReplyAdapter;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.CommentBean;
import com.ruanshuo.news.bean.ReplyBean;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.view.CircleImageView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ReplyActivity extends BaseActivity {
	private ImageButton btnBack;
	private Intent intent;
	private EditText et_reply;
	private Button btnSendReply;
	private ListReplyAdapter adapter;
	private ListView lvReply;
	private CircleImageView civ;
	private TextView tvName;
	private TextView tvTime;
	private TextView tvContent;
	private ImageButton btnPraise;
	private TextView tvPraiseNum;
	private List<String> praiseUids;
	private _User user;
	private LinearLayout ll;
	private TextView tvNoData;

	/*
	 * 初始化ui
	 */
	@Override
	public void initViews() {
		setContentView(R.layout.activity_reply);
		et_reply = (EditText) findViewById(R.id.et_reply);
		btnSendReply = (Button) findViewById(R.id.btn_send_reply);
		lvReply = (ListView) findViewById(R.id.lv_reply);
		btnBack = (ImageButton) findViewById(R.id.imgbtn_back_reply);
		civ = (CircleImageView) findViewById(R.id.civ_comment_reply);
		tvName = (TextView) findViewById(R.id.tv_uname_comment_reply);
		tvTime = (TextView) findViewById(R.id.tv_time_comment_reply);
		tvContent = (TextView) findViewById(R.id.tv_comment_reply);
		btnPraise = (ImageButton) findViewById(R.id.imgBtn_praise_reply);
		tvPraiseNum = (TextView) findViewById(R.id.tv_praise_num_reply);
		ll = (LinearLayout) findViewById(R.id.ll_send_reply_reply);
		tvNoData = (TextView) findViewById(R.id.tv_no_data_reply);
	}

	/*
	 * 初始化数据
	 */
	@Override
	public void initDatas() {
		user = BmobUser.getCurrentUser(getApplicationContext(), _User.class);

		intent = getIntent();
		BmobQuery<CommentBean> cQuery = new BmobQuery<CommentBean>();
		cQuery.addWhereEqualTo("commentId", intent.getStringExtra("commentId"));
		cQuery.getObject(getApplicationContext(), intent.getStringExtra("commentId"), new GetListener<CommentBean>() {

			@Override
			public void onFailure(int arg0, String arg1) {

			}

			@Override
			public void onSuccess(CommentBean arg0) {
				tvName.setText(arg0.getUname());
				tvTime.setText(arg0.getCreatedAt());
				tvContent.setText(arg0.getCommentContent());
				if (user != null && arg0.getUser().getObjectId().equals(user.getObjectId())) {
					ll.setVisibility(View.GONE);
				}
				BmobQuery<_User> q = new BmobQuery<_User>();
				q.getObject(getApplicationContext(), arg0.getUser().getObjectId(), new GetListener<_User>() {

					@Override
					public void onFailure(int arg0, String arg1) {

					}

					@Override
					public void onSuccess(_User u) {
						if (u.getPic() != null) {
							DisplayImageOptions options = new DisplayImageOptions.Builder()
									.showImageOnLoading(R.drawable.default_round_head)
									.showImageOnFail(R.drawable.default_round_head).cacheInMemory(true)
									.cacheOnDisk(true).build();

							ImageLoader.getInstance().displayImage(u.getPic(), civ, options);
						}

					}
				});

				if (arg0.getPraiseNum() == null) {
					tvPraiseNum.setText("0");
				} else {
					tvPraiseNum.setText(arg0.getPraiseNum() + "");
				}

			}
		});
		BmobQuery<ReplyBean> query = new BmobQuery<ReplyBean>();
		query.addWhereEqualTo("commentId", intent.getStringExtra("commentId"));
		query.setLimit(50);
		query.findObjects(getApplicationContext(), new FindListener<ReplyBean>() {

			@Override
			public void onSuccess(List<ReplyBean> arg0) {
				if (arg0.size() == 0) {
					tvNoData.setVisibility(View.VISIBLE);
				} else {
					adapter = new ListReplyAdapter(getApplicationContext(), arg0);
					lvReply.setAdapter(adapter);
				}

			}

			@Override
			public void onError(int arg0, String arg1) {
				tvNoData.setVisibility(View.VISIBLE);
			}
		});

	}

	/*
	 * 监听器
	 */
	@Override
	public void initListener() {
		// 点赞
		btnPraise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				praiseUids = intent.getStringArrayListExtra("praiseUids");
				int praiseNum = intent.getIntExtra("praiseNum", 0);
				if (praiseNum == 0) {
					praiseUids = new ArrayList<String>();
					CommentBean commentBean = new CommentBean();
					commentBean.setPraiseNum(1);
					commentBean.update(getApplicationContext(), intent.getStringExtra("commentId"),
							new UpdateListener() {

						@Override
						public void onSuccess() {

						}

						@Override
						public void onFailure(int arg0, String arg1) {

						}
					});
					commentBean.addUnique("praiseUids", user.getObjectId());
					commentBean.update(getApplicationContext(), intent.getStringExtra("commentId"),
							new UpdateListener() {

						@Override
						public void onSuccess() {

						}

						@Override
						public void onFailure(int arg0, String arg1) {

						}
					});
					tvPraiseNum.setText("1");
					btnPraise.setImageResource(R.drawable.praise_pressed);
				} else {
					if (!praiseUids.contains(user.getObjectId())) {

						CommentBean commentBean = new CommentBean();
						commentBean.setPraiseNum(praiseNum + 1);

						commentBean.update(getApplicationContext(), intent.getStringExtra("commentId"),
								new UpdateListener() {

							@Override
							public void onSuccess() {

							}

							@Override
							public void onFailure(int arg0, String arg1) {

							}
						});

						commentBean.addUnique("praiseUids", user.getObjectId());
						commentBean.update(getApplicationContext(), intent.getStringExtra("commentId"),
								new UpdateListener() {

							@Override
							public void onSuccess() {

							}

							@Override
							public void onFailure(int arg0, String arg1) {

							}
						});

						int num = praiseNum + 1;
						tvPraiseNum.setText(num + "");
						btnPraise.setImageResource(R.drawable.praise_pressed);

					} else {
						Toast.makeText(getApplicationContext(), "你已赞过", Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
		// 返回
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		// 发送回复
		btnSendReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String replyContent = et_reply.getText().toString();
				if (replyContent.isEmpty()) {
					et_reply.setHint("请输入内容");

				} else if (BmobUser.getCurrentUser(getApplicationContext(), _User.class) == null) {
					Intent i = new Intent(ReplyActivity.this, LoginActivity.class);
					startActivity(i);
				} else {
					ReplyBean replyBean = new ReplyBean();
					replyBean.setReplyContent(replyContent);
					if (user.getPic() != null) {
						replyBean.setReplyUpic(user.getPic());

					}
					replyBean.setCommentUserId(intent.getStringExtra("userId"));
					replyBean.setUser(user);
					replyBean.setReplyUname(user.getUsername());
					replyBean.setCommentId(intent.getStringExtra("commentId"));
					replyBean.save(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							Toast.makeText(getApplicationContext(), "回复成功", Toast.LENGTH_SHORT).show();
							et_reply.setText("");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(getApplicationContext(), "回复失败：" + arg1, Toast.LENGTH_SHORT).show();

						}
					});
				}

			}
		});

	}

}
