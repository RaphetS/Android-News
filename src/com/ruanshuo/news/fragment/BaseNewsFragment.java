package com.ruanshuo.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ruanshuo.news.R;
import com.ruanshuo.news.activity.DetailsActivity_;
import com.ruanshuo.news.activity.ImageDetailActivity_;
import com.ruanshuo.news.adapter.ListNewsAdapter;
import com.ruanshuo.news.bean.NewModle;
import com.ruanshuo.news.http.NewListJson;
import com.ruanshuo.news.http.Url;
import com.ruanshuo.news.nao.NewsNao;
import com.ruanshuo.news.nao.impl.NewsNaoImpl;
import com.ruanshuo.news.utils.CacheUtils;

import com.ruanshuo.news.utils.NetWorkUtil;
import com.ruanshuo.news.wedget.slideingactivity.IntentUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

public class BaseNewsFragment extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore {

	private static final int LOAD_MORE = 0x110;
	private static final int LOAD_REFREASH = 0x111;
	private static final int TIP_ERROR_NO_NETWORK = 0X112;
	private static final int TIP_ERROR_SERVER = 0X113;
	private boolean isFirstIn = true;
	private ListNewsAdapter mAdapter;
	private XListView xListView;
	private int newsType;
	private int currentPage = 1;
	private Handler handler;
	private NewsNao newsNao;
	private List<NewModle> mDatas = new ArrayList<NewModle>();
	private String itemId;
	private int index = 0;
	private List<NewModle> listNewmodles;

	public BaseNewsFragment(String itemId) {
		super();
		this.itemId = itemId;
		this.newsNao = new NewsNaoImpl();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_base_news, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
		initListener();
	}

	private void initListener() {
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				NewModle newModle = listNewmodles.get(position - 1);
				enterDetailActivity(newModle);
			}
		});

	}

	protected void enterDetailActivity(NewModle newModle) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("newModle", newModle);

		Class<?> class1;
		if (newModle.getImagesModle() != null && newModle.getImagesModle().getImgList().size() > 1) {
			class1 = ImageDetailActivity_.class;
		} else {
			class1 = DetailsActivity_.class;
		}
		openActivity(class1, bundle, 0);
	}

	private void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
		Intent intent = new Intent(getActivity(), pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		if (requestCode == 0) {
			// IntentUtils.startPreviewActivity(this, intent, 0);
			startActivity(intent);
		} else {
			// IntentUtils.startPreviewActivity(this, intent, requestCode);
			startActivityForResult(intent, requestCode);
		}
	}

	private void initView() {
		xListView = (XListView) getView().findViewById(R.id.xlv_base_news);

	}

	public void initData() {
		listNewmodles = new ArrayList<NewModle>();
		handler = new Handler();
		if (NetWorkUtil.hasNetWrok(getContext())) {
			mAdapter = new ListNewsAdapter(mDatas, getContext());
			xListView.setAdapter(mAdapter);
			xListView.setPullLoadEnable(this);
			xListView.setPullRefreshEnable(this);
			xListView.startRefresh();
		} else {
			Toast.makeText(getContext(), "无网络连接", Toast.LENGTH_LONG).show();
			String json = CacheUtils.getCache(itemId + currentPage, getContext());
			if (json != null) {
				List<NewModle> mDatas = NewListJson.instance(getActivity()).readJsonNewModles(json, getNewsType());

				mAdapter = new ListNewsAdapter(mDatas, getContext());
				xListView.setAdapter(mAdapter);
				xListView.setPullLoadEnable(this);
				xListView.setPullRefreshEnable(this);
				xListView.NotRefreshAtBegin();
			}
		}

		if (isFirstIn) {
			xListView.startRefresh();
			isFirstIn = false;
		} else {
			xListView.NotRefreshAtBegin();
		}

	}

	public String getUrl(String index, String itemId) {
		switch (itemId) {
		case Url.BeiJingId:
			String urlBj = Url.Local + itemId + "/" + index + Url.endUrl;
			return urlBj;

		case "":
			String urlHot = Url.TopUrl + Url.TopId + "/" + index + Url.endUrl;
			return urlHot;

		case Url.SheHuiId:
			String urlSH = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlSH;
		case Url.JunShiId:
			String urlJS = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlJS;
		case Url.CaiJingId:
			String urlCJ = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlCJ;
		case Url.KeJiId:
			String urlKJ = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlKJ;
		case Url.TiYuId:
			String urlTY = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlTY;
		case Url.YuLeId:
			String urlYL = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlYL;
		case Url.ShiShangId:
			String urlSS = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlSS;
		case Url.XiaoHuaId:
			String urlXH = Url.CommonUrl + itemId + "/" + index + Url.endUrl;
			return urlXH;
		}
		return null;

	}

	private String getNewsType() {
		switch (itemId) {
		case Url.BeiJingId:

			return "北京";

		case "":

			return Url.TopId;

		case Url.SheHuiId:
			return Url.SheHuiId;
		case Url.JunShiId:

			return Url.JunShiId;
		case Url.CaiJingId:

			return Url.CaiJingId;
		case Url.KeJiId:

			return Url.KeJiId;
		case Url.TiYuId:

			return Url.TiYuId;
		case Url.YuLeId:

			return Url.YuLeId;
		case Url.ShiShangId:

			return Url.ShiShangId;
		case Url.XiaoHuaId:

			return Url.XiaoHuaId;
		}
		return null;
	}

	@Override
	public void onLoadMore() {
		if (NetWorkUtil.hasNetWrok(getContext())) {

			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					currentPage++;
					index = index + 20;
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.send(HttpMethod.GET, getUrl(index + "", itemId), new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String json = responseInfo.result;
							CacheUtils.setCache(itemId + currentPage, json, getContext());

							List<NewModle> mDatas = NewListJson.instance(getActivity()).readJsonNewModles(json,
									getNewsType());
							listNewmodles.addAll(mDatas);
							mAdapter.addAll(mDatas);

							xListView.stopLoadMore();
						}

						@Override
						public void onFailure(HttpException error, String msg) {

						}

					});
				}
			}, 2000);
		} else {
			currentPage++;
			String json = CacheUtils.getCache(itemId + currentPage, getContext());
			if (json != null) {
				List<NewModle> mDatas = NewListJson.instance(getActivity()).readJsonNewModles(json, getNewsType());
				mAdapter.addAll(mDatas);
				xListView.stopLoadMore();
			} else {
				xListView.stopLoadMore();
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
					httpUtils.send(HttpMethod.GET, getUrl(0 + "", itemId), new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String json = responseInfo.result;
							Log.i("请求结果", json);
							CacheUtils.setCache(itemId + currentPage, json, getContext());
							List<NewModle> list = NewListJson.instance(getActivity()).readJsonNewModles(json,
									getNewsType());
							listNewmodles.clear();
							listNewmodles.addAll(list);
							Log.i("解析结果", list.size() + ">>>>>>>>>>>>>>>>>>>");

							mAdapter.setData(list);
							mAdapter.notifyDataSetChanged();
							xListView.stopRefresh();
						}

						@Override
						public void onFailure(HttpException error, String msg) {

						}

					});
				}

			}, 2000);
		} else {
			String json = CacheUtils.getCache(itemId + currentPage, getContext());
			if (json != null) {
				List<NewModle> mDatas = NewListJson.instance(getActivity()).readJsonNewModles(json, getNewsType());
				mAdapter.setData(mDatas);
				mAdapter.notifyDataSetChanged();
				xListView.stopRefresh();
			} else {
				xListView.stopRefresh();
			}

		}
	}

}
