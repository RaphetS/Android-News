package com.ruanshuo.news.nao.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ruanshuo.news.bean.NewsItem;
import com.ruanshuo.news.nao.NewsNao;

public class NewsNaoImpl implements NewsNao {
	String url = "http://10.0.2.2:8080/NewsServer/NewsServlet";
	private List<NewsItem> newsDatas = new ArrayList<NewsItem>();

	// @Override
	// public List<NewsItem> getNewsItem(int newsType) {
	// List<NewsItem> newsItems = new ArrayList<NewsItem>();
	// HttpClient client = new DefaultHttpClient();
	// HttpGet get = new HttpGet(url + "?newsType=" + newsType);
	//
	// try {
	//
	// HttpResponse response = client.execute(get);
	//
	// Log.i("xxxxxxx", response.getStatusLine().getStatusCode() + "");
	// if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	// String json = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	// Gson gson = new Gson();
	// newsItems = gson.fromJson(json, new TypeToken<List<NewsItem>>() {
	// }.getType());
	// return newsItems;
	// } else {
	// return null;
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	//
	// }

	public List<NewsItem> getNewsItem(int newsType, int currentPage) {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET,
				"http://news.gxgjean.com/open/c_list?cid=" + newsType + "&p=" + currentPage + "&type=json",
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String json = responseInfo.result;
						Gson gson = new Gson();
						newsDatas = gson.fromJson(json, new TypeToken<List<NewsItem>>() {
						}.getType());

					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}

				});
		return newsDatas;

	}

}
