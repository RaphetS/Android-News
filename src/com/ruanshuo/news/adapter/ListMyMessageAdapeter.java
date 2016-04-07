package com.ruanshuo.news.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
import com.ruanshuo.news.bean.CommentBean;
import com.ruanshuo.news.bean.ReplyBean;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

public class ListMyMessageAdapeter extends BaseAdapter {
	private Context ctx;
	private LayoutInflater inflater;
	private List<ReplyBean> replys;

	public ListMyMessageAdapeter(Context context, List<ReplyBean> list) {
		inflater = LayoutInflater.from(context);
		replys = list;
		ctx = context;
	}

	@Override
	public int getCount() {

		return replys.size();
	}

	@Override
	public Object getItem(int position) {

		return replys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_my_message, null);
			holder = new ViewHolder();
			holder.civ = (CircleImageView) convertView.findViewById(R.id.civ_my_message);
			holder.tvReplyName = (TextView) convertView.findViewById(R.id.tv_replyName_message);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time_message);
			holder.tvReplyContent = (TextView) convertView.findViewById(R.id.tv_reply_message);
			holder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment_message);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置头像
		BmobQuery<_User> userQuery = new BmobQuery<_User>();
		userQuery.getObject(ctx, replys.get(position).getUser().getObjectId(), new GetListener<_User>() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(_User arg0) {
				if (arg0.getPic() != null) {
					DisplayImageOptions options = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.default_round_head)
							.showImageOnFail(R.drawable.default_round_head).cacheInMemory(true).cacheOnDisk(true)
							.build();

					ImageLoader.getInstance().displayImage(arg0.getPic(), holder.civ, options);
				}
				// 设置回复的用户名称
				holder.tvReplyName.setText(arg0.getUsername());

			}
		});
		holder.tvTime.setText(replys.get(position).getUpdatedAt());
		holder.tvReplyContent.setText(replys.get(position).getReplyContent());
		BmobQuery<CommentBean> commentQuery = new BmobQuery<CommentBean>();
		commentQuery.getObject(ctx, replys.get(position).getCommentId(), new GetListener<CommentBean>() {

			@Override
			public void onSuccess(CommentBean arg0) {
				holder.tvComment.setText("我："+arg0.getCommentContent());

			}

			@Override
			public void onFailure(int arg0, String arg1) {

			}
		});
		return convertView;
	}

	class ViewHolder {
		CircleImageView civ;
		TextView tvReplyName;
		TextView tvTime;
		TextView tvReplyContent;
		TextView tvComment;

	}
}
