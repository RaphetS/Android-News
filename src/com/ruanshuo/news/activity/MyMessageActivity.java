package com.ruanshuo.news.activity;

import java.util.List;

import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.ListMyMessageAdapeter;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.ReplyBean;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.utils.NetWorkUtil;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MyMessageActivity extends BaseActivity {
	private ImageButton btnBack;
	private _User currentUser;

	private ListView lvMyMessage;
	private TextView tvNoData;

	@Override
	public void initViews() {
		setContentView(R.layout.activity_my_message);
		btnBack = (ImageButton) findViewById(R.id.imgbtn_back_message);
		lvMyMessage = (ListView) findViewById(R.id.lv_my_message);
		tvNoData = (TextView) findViewById(R.id.tv_no_data_message);
	}

	@Override
	public void initDatas() {
		if (!NetWorkUtil.hasNetWrok(getApplicationContext())) {
			tvNoData.setText("无网络连接");
			tvNoData.setVisibility(View.VISIBLE);
		} else {

			currentUser = BmobUser.getCurrentUser(getApplicationContext(), _User.class);
			BmobQuery<ReplyBean> replyQuery = new BmobQuery<ReplyBean>();
			replyQuery.addWhereEqualTo("commentUserId", currentUser.getObjectId());
			replyQuery.findObjects(getApplicationContext(), new FindListener<ReplyBean>() {

				@Override
				public void onError(int arg0, String arg1) {
					tvNoData.setVisibility(View.VISIBLE);
				}

				@Override
				public void onSuccess(List<ReplyBean> arg0) {
					if (arg0 == null) {
						tvNoData.setVisibility(View.VISIBLE);
					} else {
						ListMyMessageAdapeter adapeter = new ListMyMessageAdapeter(getApplicationContext(), arg0);
						lvMyMessage.setAdapter(adapeter);
					}

				}
			});
		}
		/*
		 * BmobQuery<CommentBean> commentQuery = new BmobQuery<CommentBean>();
		 * commentQuery.addWhereEqualTo("user", new BmobPointer(currentUser));
		 * commentQuery.findObjects(getApplicationContext(), new
		 * FindListener<CommentBean>() {
		 * 
		 * @Override public void onSuccess(List<CommentBean> arg0) {
		 * Log.e("List<CommentBean>消息", arg0.size()+""); for (CommentBean
		 * commentBean : arg0) { BmobQuery<ReplyBean> replyQuery = new
		 * BmobQuery<ReplyBean>(); replyQuery.addWhereEqualTo("commentId",
		 * commentBean.getObjectId());
		 * replyQuery.findObjects(getApplicationContext(), new
		 * FindListener<ReplyBean>() {
		 * 
		 * @Override public void onError(int arg0, String arg1) { }
		 * 
		 * @Override public void onSuccess(List<ReplyBean> a) {
		 * Log.e("List<ReplyBean>消息", a.size() + ""); replys.addAll(a);
		 * 
		 * } });
		 * 
		 * } if (replys.size() == 0) { Log.e("我的消息", "success");
		 * tvNoData.setVisibility(View.VISIBLE); } else { ListMyMessageAdapeter
		 * adapeter = new ListMyMessageAdapeter(getApplicationContext(),
		 * replys); lvMyMessage.setAdapter(adapeter); }
		 * 
		 * }
		 * 
		 * @Override public void onError(int arg0, String arg1) {
		 * tvNoData.setVisibility(View.VISIBLE); } });
		 */
	}

	@Override
	public void initListener() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
