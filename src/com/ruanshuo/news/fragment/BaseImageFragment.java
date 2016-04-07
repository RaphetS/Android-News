package com.ruanshuo.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.ListImageAdapter;
import com.ruanshuo.news.adapter.ListNewsAdapter;
import com.ruanshuo.news.bean.NewModle;
import com.ruanshuo.news.bean.PhotoModle;
import com.ruanshuo.news.bean.PicuterModle;
import com.ruanshuo.news.http.NewListJson;
import com.ruanshuo.news.http.PhotoListJson;
import com.ruanshuo.news.http.PicuterSinaJson;
import com.ruanshuo.news.http.Url;
import com.ruanshuo.news.utils.CacheUtils;
import com.ruanshuo.news.utils.NetWorkUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

public class BaseImageFragment extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore {
	private View view;
	private XListView xlv;
	private Handler handler;
	private ListImageAdapter adapter;
	private List<PicuterModle> mDatas;
	private boolean isFirstIn = true;
	private int index;
	private int currentPage = 1;
	private String itemId;
	private int type;// type=1:精选 2美图 3趣图 4故事
	private String TuPianType = "TuPianType";

	public BaseImageFragment(int type) {
		this.type = type;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_base_image, null);
		findView();
		initData();
		InitListener();
		return view;
	}

	private void findView() {
		xlv = (XListView) view.findViewById(R.id.xlv_base_img);

	}

	private void initData() {
		mDatas = new ArrayList<PicuterModle>();

		handler = new Handler();

		if (NetWorkUtil.hasNetWrok(getContext())) {
			adapter = new ListImageAdapter(mDatas, getContext());
			xlv.setAdapter(adapter);
			xlv.setPullLoadEnable(this);
			xlv.setPullRefreshEnable(this);
			xlv.startRefresh();
		} else {
			adapter = new ListImageAdapter(mDatas, getContext());
			Toast.makeText(getContext(), "无网络连接", Toast.LENGTH_LONG).show();
			String json = CacheUtils.getCache(itemId + currentPage, getContext());
			if (json != null) {
				List<PicuterModle> list = PicuterSinaJson.instance(getActivity()).readJsonPhotoListModles(json);
				adapter.setData(list);
				xlv.setAdapter(adapter);
				xlv.setPullLoadEnable(this);
				xlv.setPullRefreshEnable(this);
				xlv.NotRefreshAtBegin();
			}
		}
		if (isFirstIn) {
			xlv.startRefresh();
			isFirstIn = false;
		} else {
			xlv.NotRefreshAtBegin();
		}

	}

	private void InitListener() {

	}

	@Override
	public void onLoadMore() {
		if (NetWorkUtil.hasNetWrok(getContext())) {

			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					currentPage++;
					index = index + 5;
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.send(HttpMethod.GET, getUrl(index + "", type), new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String json = responseInfo.result;
							CacheUtils.setCache(TuPianType + type + currentPage, json, getContext());
							List<PicuterModle> list = PicuterSinaJson.instance(getActivity())
									.readJsonPhotoListModles(json);
							adapter.addAll(list);
							xlv.stopLoadMore();
						}

						@Override
						public void onFailure(HttpException error, String msg) {

						}

					});
				}
			}, 2000);
		} else {
			currentPage++;
			String json = CacheUtils.getCache(TuPianType + type + currentPage, getContext());
			if (json != null) {
				List<PicuterModle> list = PicuterSinaJson.instance(getActivity()).readJsonPhotoListModles(json);
				adapter.addAll(list);
				xlv.stopLoadMore();
			} else {
				xlv.stopLoadMore();
			}

		}
	}

	@Override
	public void onRefresh() {

		if (NetWorkUtil.hasNetWrok(getContext())) {

			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.send(HttpMethod.GET, getUrl(0 + "", type), new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String json = responseInfo.result;
							Log.i("请求结果", json);
							CacheUtils.setCache(TuPianType + type + currentPage, json, getContext());
							List<PicuterModle> list = PicuterSinaJson.instance(getActivity())
									.readJsonPhotoListModles(json);

							adapter.setData(list);

							xlv.stopRefresh();
						}

						@Override
						public void onFailure(HttpException error, String msg) {

						}

					});
				}

			}, 2000);
		} else {
			String json = CacheUtils.getCache(TuPianType + type + currentPage, getContext());
			if (json != null) {
				List<PicuterModle> list = PicuterSinaJson.instance(getActivity()).readJsonPhotoListModles(json);
				adapter.setData(list);
				xlv.stopRefresh();
			} else {
				xlv.stopRefresh();
			}
		}
	}

	protected String getUrl(String index, int type) {
		switch (type) {
		case 1:
			String urlJX = Url.JINGXUAN_ID + index;
			return urlJX;
		case 2:
			String urlMT = Url.MEITU_ID + index;
			return urlMT;
		case 3:
			String urlQT = Url.QUTU_ID + index;
			return urlQT;
		case 4:
			String urlGS = Url.GUSHI_ID + index;
			return urlGS;
		}
		return null;

	}
}
