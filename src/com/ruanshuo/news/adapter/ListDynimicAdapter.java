package com.ruanshuo.news.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
import com.ruanshuo.news.bean.DynimicBean;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class ListDynimicAdapter extends BaseAdapter {
	private List<DynimicBean> dynimicBeans;
	private Context context;
	private LayoutInflater inflater;
	private DisplayImageOptions options;

	public ListDynimicAdapter(Context ctx, List<DynimicBean> datas) {
		context = ctx;
		dynimicBeans = datas;
		inflater = LayoutInflater.from(ctx);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading_pic)
				.showImageOnFail(R.drawable.load_pic_fail).cacheInMemory(true).cacheOnDisk(true).build();
	}

	public void addAll(List<DynimicBean> datas) {
		this.dynimicBeans.addAll(datas);
	}

	public void setData(List<DynimicBean> datas) {
		this.dynimicBeans.clear();
		this.dynimicBeans.addAll(datas);
	}

	@Override
	public int getCount() {

		return dynimicBeans.size();
	}

	@Override
	public Object getItem(int arg0) {

		return dynimicBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.item_dynimic, null);
			holder = new ViewHolder();
			holder.civ = (CircleImageView) view.findViewById(R.id.civ_dynimic);
			holder.tvName = (TextView) view.findViewById(R.id.tv_uname_dynimic);
			holder.tvTime = (TextView) view.findViewById(R.id.tv_time_dynimic);
			holder.tvContent = (TextView) view.findViewById(R.id.tv_dynimic_content);
			holder.iv1 = (ImageView) view.findViewById(R.id.iv_1_dynimic);
			holder.iv2 = (ImageView) view.findViewById(R.id.iv_2_dynimic);
			holder.iv3 = (ImageView) view.findViewById(R.id.iv_3_dynimic);
			holder.btnShare=(ImageButton) view.findViewById(R.id.imgbtn_share_dynimic);
			holder.btnPraise=(ImageButton) view.findViewById(R.id.imgBtn_praise);
			holder.reply=(ImageButton) view.findViewById(R.id.imgbtn_reply_dynimic);
			holder.tvPraiseNum=(TextView) view.findViewById(R.id.tv_praise_num_dynimic);
			holder.tvReply=(TextView) view.findViewById(R.id.tv_reply_dynimic);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvTime.setText(dynimicBeans.get(position).getCreatedAt());
		holder.tvContent.setText(dynimicBeans.get(position).getDynimicContent());
		
		if (dynimicBeans.get(position).getDynimicPics() != null
				&& dynimicBeans.get(position).getDynimicPics().size() == 1) {
			holder.iv1.setVisibility(View.VISIBLE);
			holder.iv2.setVisibility(View.GONE);
			holder.iv3.setVisibility(View.GONE);
			ImageLoader.getInstance().displayImage(dynimicBeans.get(position).getDynimicPics().get(0), holder.iv1,
					options);
		} else if (dynimicBeans.get(position).getDynimicPics() != null
				&& dynimicBeans.get(position).getDynimicPics().size() == 2) {
			holder.iv1.setVisibility(View.VISIBLE);
			holder.iv2.setVisibility(View.VISIBLE);
			holder.iv3.setVisibility(View.GONE);
			ImageLoader.getInstance().displayImage(dynimicBeans.get(position).getDynimicPics().get(0), holder.iv1,
					options);
			ImageLoader.getInstance().displayImage(dynimicBeans.get(position).getDynimicPics().get(1), holder.iv2,
					options);

		} else if (dynimicBeans.get(position).getDynimicPics() != null
				&& dynimicBeans.get(position).getDynimicPics().size() == 3) {
			holder.iv1.setVisibility(View.VISIBLE);
			holder.iv2.setVisibility(View.VISIBLE);
			holder.iv3.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(dynimicBeans.get(position).getDynimicPics().get(0), holder.iv1,
					options);
			ImageLoader.getInstance().displayImage(dynimicBeans.get(position).getDynimicPics().get(1), holder.iv2,
					options);
			ImageLoader.getInstance().displayImage(dynimicBeans.get(position).getDynimicPics().get(1), holder.iv3,
					options);
		} else {
			holder.iv1.setVisibility(View.GONE);
			holder.iv2.setVisibility(View.GONE);
			holder.iv3.setVisibility(View.GONE);
		}
		BmobQuery<_User> userQuery = new BmobQuery<_User>();
		userQuery.getObject(context, dynimicBeans.get(position).getUser().getObjectId(), new GetListener<_User>() {

			@Override
			public void onFailure(int arg0, String arg1) {

			}

			@Override
			public void onSuccess(_User arg0) {
				ImageLoader.getInstance().displayImage(arg0.getPic(), holder.civ, options);
				holder.tvName.setText(arg0.getUsername());

			}
		});
		holder.reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		return view;
	}

	class ViewHolder {
		CircleImageView civ;
		TextView tvName;
		TextView tvTime;
		TextView tvContent;
		ImageView iv1;
		ImageView iv2;
		ImageView iv3;
		ImageButton btnShare;
		ImageButton btnPraise;
		TextView tvPraiseNum;
		ImageButton reply;
		TextView tvReply;
	}
}
