package com.ruanshuo.news.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
import com.ruanshuo.news.activity.ReplyActivity;
import com.ruanshuo.news.bean.CommentBean;
import com.ruanshuo.news.bean.ReplyBean;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.view.CircleImageView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

public class ListCommentAdapter extends BaseAdapter {
	private _User user;
	private LayoutInflater inflater;
	private List<CommentBean> mDatas;
	private Activity mActivity;
	private Context context;
	private List<String> praiseUids;

	public ListCommentAdapter(Activity activity, Context ctx, List<CommentBean> mBeans) {
		context = ctx;
		inflater = LayoutInflater.from(ctx);
		mDatas = mBeans;
		mActivity = activity;
	}

	@Override
	public int getCount() {

		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {

		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		user = BmobUser.getCurrentUser(context, _User.class);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.comment_item, null);
			holder.tvUname = (TextView) convertView.findViewById(R.id.tv_uname_comment);
			holder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time_comment);
			holder.tvReplyNum = (TextView) convertView.findViewById(R.id.tv_reply_number);
			holder.tvPraiseNum = (TextView) convertView.findViewById(R.id.tv_praise_num);
			holder.civ = (CircleImageView) convertView.findViewById(R.id.civ_comment);
			holder.ivReply = (ImageView) convertView.findViewById(R.id.iv_reply);
			holder.btnPraise = (ImageButton) convertView.findViewById(R.id.imgBtn_praise);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.tvUname.setText(mDatas.get(position).getUname());
		holder.tvTime.setText(mDatas.get(position).getCreatedAt());
		holder.tvComment.setText(mDatas.get(position).getCommentContent());
		BmobQuery<_User> q = new BmobQuery<_User>();
		q.getObject(context, mDatas.get(position).getUser().getObjectId(), new GetListener<_User>() {

			@Override
			public void onFailure(int arg0, String arg1) {

			}

			@Override
			public void onSuccess(_User arg0) {
				if (arg0.getPic() != null) {
					DisplayImageOptions options = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.default_round_head)
							.showImageOnFail(R.drawable.default_round_head).cacheInMemory(true).cacheOnDisk(true)
							.build();

					ImageLoader.getInstance().displayImage(arg0.getPic(), holder.civ, options);
				} else {
					holder.civ.setImageResource(R.drawable.default_round_head);
				}

			}

		});

		BmobQuery<ReplyBean> query = new BmobQuery<ReplyBean>();
		query.addWhereEqualTo("commentId", mDatas.get(position).getObjectId());
		query.count(context, ReplyBean.class, new CountListener() {

			@Override
			public void onFailure(int arg0, String arg1) {

			}

			@Override
			public void onSuccess(int arg0) {
				if (arg0 > 0) {
					holder.tvReplyNum.setVisibility(View.VISIBLE);
					holder.tvReplyNum.setText(arg0 + "");

				}

			}
		});
		if (mDatas.get(position).getPraiseNum() != null) {

			holder.tvPraiseNum.setText(mDatas.get(position).getPraiseNum() + "");
		} else {
			holder.tvPraiseNum.setText("0");
		}
		holder.btnPraise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mDatas.get(position).getPraiseNum() == null) {
					praiseUids = new ArrayList<String>();
					CommentBean commentBean = new CommentBean();
					commentBean.setPraiseNum(1);
					commentBean.update(context, mDatas.get(position).getObjectId(), new UpdateListener() {

						@Override
						public void onSuccess() {

						}

						@Override
						public void onFailure(int arg0, String arg1) {

						}
					});
					commentBean.addUnique("praiseUids", user.getObjectId());
					commentBean.update(context, mDatas.get(position).getObjectId(), new UpdateListener() {

						@Override
						public void onSuccess() {

						}

						@Override
						public void onFailure(int arg0, String arg1) {

						}
					});
					holder.btnPraise.setImageResource(R.drawable.praise_pressed);
					holder.btnPraise.setTag("已赞");
					holder.tvPraiseNum.setText("1");
				} else {
					praiseUids = mDatas.get(position).getPraiseUids();
					if (!praiseUids.contains(user.getObjectId())) {
						CommentBean commentBean = new CommentBean();
						commentBean.setPraiseNum(mDatas.get(position).getPraiseNum() + 1);
						commentBean.update(context, mDatas.get(position).getObjectId(), new UpdateListener() {

							@Override
							public void onSuccess() {

							}

							@Override
							public void onFailure(int arg0, String arg1) {

							}
						});

						commentBean.addUnique("praiseUids", user.getObjectId());
						commentBean.update(context, mDatas.get(position).getObjectId(), new UpdateListener() {

							@Override
							public void onSuccess() {

							}

							@Override
							public void onFailure(int arg0, String arg1) {

							}
						});
						holder.btnPraise.setImageResource(R.drawable.praise_pressed);
						holder.btnPraise.setTag("已赞");
						int num = mDatas.get(position).getPraiseNum() + 1;
						holder.tvPraiseNum.setText(num + "");
					} else if (holder.btnPraise.getTag().equals("已赞")) {

						Toast.makeText(context, "你已赞过", Toast.LENGTH_SHORT).show();
					}
				}

			}

		});

		holder.ivReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, ReplyActivity.class);
				intent.putExtra("commentId", mDatas.get(position).getObjectId());
				intent.putExtra("praiseNum", mDatas.get(position).getPraiseNum());
				intent.putExtra("userId", mDatas.get(position).getUser().getObjectId());
				intent.putStringArrayListExtra("praiseUids", (ArrayList<String>) mDatas.get(position).getPraiseUids());
				mActivity.startActivity(intent);

			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView tvUname;
		TextView tvPraiseNum;
		TextView tvTime;
		TextView tvComment;
		TextView tvReplyNum;
		ImageButton btnPraise;
		CircleImageView civ;
		ImageView ivReply;

	}

}
