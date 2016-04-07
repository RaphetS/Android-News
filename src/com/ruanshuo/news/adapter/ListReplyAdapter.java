package com.ruanshuo.news.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
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

public class ListReplyAdapter extends BaseAdapter {
	private List<ReplyBean> mDatas;
	private Context context;
	private LayoutInflater inflater;

	public ListReplyAdapter(Context ctx, List<ReplyBean> list) {
		context = ctx;
		mDatas = list;
		inflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {

		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {

		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.reply_item, null);
			holder.civ = (CircleImageView) view.findViewById(R.id.civ_reply);
			holder.tvUname = (TextView) view.findViewById(R.id.tv_replyUser);
			holder.tvTime = (TextView) view.findViewById(R.id.tv_reply_time);
			holder.tvContent = (TextView) view.findViewById(R.id.tv_reply_content);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		BmobQuery<_User> q = new BmobQuery<_User>();
		q.getObject(context, mDatas.get(position).getUser().getObjectId(), new GetListener<_User>() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(_User u) {
				if (u.getPic()!=null) {
					DisplayImageOptions options = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.default_round_head)
							.showImageOnFail(R.drawable.default_round_head).cacheInMemory(true).cacheOnDisk(true).build();

					ImageLoader.getInstance().displayImage(u.getPic(), holder.civ, options);
				}
				
			}
		});
		
		holder.tvUname.setText(mDatas.get(position).getReplyUname());
		holder.tvContent.setText(mDatas.get(position).getReplyContent());
		holder.tvTime.setText(mDatas.get(position).getCreatedAt());
		return view;
	}

	class ViewHolder {
		CircleImageView civ;
		TextView tvUname;
		TextView tvTime;
		TextView tvContent;

	}
}
