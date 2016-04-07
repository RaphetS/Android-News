package com.ruanshuo.news.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruanshuo.news.R;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.Collection;
import com.ruanshuo.news.bean.CommentBean;
import com.ruanshuo.news.bean._User;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class NewsDetailActivity extends BaseActivity {
	private WebView webView;
	private EditText etComment;
	private Button btnSendComment;
	private TextView tvCommentNo;
	private Intent intent;
	private ImageButton btnBack;
	private ImageView iv;
	private ImageView ivCollect;
	private _User currentUser;

	/*
	 * 初始化ui
	 */
	@Override
	public void initViews() {
		setContentView(R.layout.activity_news_detail);
		webView = (WebView) findViewById(R.id.webView);
		etComment = (EditText) findViewById(R.id.et_comment);
		btnSendComment = (Button) findViewById(R.id.btn_send_comment);
		tvCommentNo = (TextView) findViewById(R.id.tv_comment_number);
		iv = (ImageView) findViewById(R.id.iv_to_comment);
		btnBack = (ImageButton) findViewById(R.id.imgbtn_back_news_detail);
		ivCollect = (ImageView) findViewById(R.id.iv_collection);
	}

	/*
	 * 初始化数据
	 */
	@Override
	public void initDatas() {
		currentUser = BmobUser.getCurrentUser(getApplicationContext(), _User.class);
		intent = getIntent();
		/*
		 * 查询评论的条数
		 */
		BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();
		query.addWhereEqualTo("newsId", intent.getStringExtra("newsId"));
		query.count(getApplicationContext(), CommentBean.class, new CountListener() {

			@Override
			public void onFailure(int arg0, String arg1) {

			}

			@Override
			public void onSuccess(int arg0) {
				if (arg0 > 0) {
					tvCommentNo.setVisibility(View.VISIBLE);
					tvCommentNo.setText(arg0 + "");
				}

			}
		});

		String link = intent.getStringExtra("link");
		/*
		 * 设置webview
		 */
		initWebView();
		webView.loadUrl(link);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		if (currentUser != null) {
			BmobQuery<_User> query2 = new BmobQuery<_User>();
			query2.getObject(getApplicationContext(), currentUser.getObjectId(), new GetListener<_User>() {

				@Override
				public void onFailure(int arg0, String arg1) {

				}

				@Override
				public void onSuccess(_User arg0) {

					List<String> collectionNewsIds = arg0.getCollectionNewsIds();
					if (collectionNewsIds != null) {
						Log.e("newsIds>>>>>>", collectionNewsIds.toString());
						if (collectionNewsIds.contains(intent.getStringExtra("newsId"))) {
							ivCollect.setImageResource(R.drawable.collection_checked);
							ivCollect.setTag("已收藏");

						} else {
							ivCollect.setTag("未收藏");
						}
					} else {
						ivCollect.setTag("未收藏");
					}
				}
			});
		}

	}

	private void initWebView() {
		webView.getSettings().setCacheMode((WebSettings.LOAD_DEFAULT));
		webView.getSettings().setJavaScriptEnabled(true);
		// 开启 DOM storage API 功能
		webView.getSettings().setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webView.getSettings().setDatabaseEnabled(true);
		String cacheDirPath = getFilesDir().getAbsolutePath() + "/webcache";
		// 设置数据库缓存路径
		webView.getSettings().setDatabasePath(cacheDirPath);
		// 设置 Application Caches 缓存目录
		webView.getSettings().setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		webView.getSettings().setAppCacheEnabled(true);

	}

	@Override
	public void initListener() {
		// 收藏
		ivCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String tag = (String) ivCollect.getTag();
				_User u = new _User();
				Collection collection = new Collection();
				if (BmobUser.getCurrentUser(getApplicationContext(), _User.class) == null) {
					Intent i = new Intent(NewsDetailActivity.this, LoginActivity.class);
					startActivity(i);
				} else if (tag.equals("未收藏")) {

					ivCollect.setImageResource(R.drawable.collection_checked);

					u.addUnique("collectionNewsIds", intent.getStringExtra("newsId"));
					u.update(getApplicationContext(), currentUser.getObjectId(), new UpdateListener() {

						@Override
						public void onSuccess() {
							Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
							ivCollect.setTag("已收藏");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(getApplicationContext(), "收藏失败" + arg1, Toast.LENGTH_SHORT).show();
						}
					});

					collection.setUserId(currentUser.getObjectId());
					collection.setNewsId(intent.getStringExtra("newsId"));
					collection.setNewsUrl(intent.getStringExtra("link"));
					collection.setNewsTitle(intent.getStringExtra("newsTitle"));
					collection.save(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							Log.i("saveListener", "收藏成功");

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.i("saveListener", "收藏失败");

						}
					});
				} else {
					ivCollect.setImageResource(R.drawable.collection);

					u.removeAll("collectionNewsIds", Arrays.asList(intent.getStringExtra("newsId")));
					u.update(getApplicationContext(), currentUser.getObjectId(), new UpdateListener() {

						@Override
						public void onSuccess() {
							Toast.makeText(getApplicationContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
							ivCollect.setTag("未收藏");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.i("取消收藏", "取消收藏失败" + arg1);

						}
					});
					BmobQuery<Collection> q1 = new BmobQuery<Collection>();
					q1.addWhereEqualTo("newsId", intent.getStringExtra("newsId"));
					BmobQuery<Collection> q2 = new BmobQuery<Collection>();
					q2.addWhereEqualTo("userId", currentUser.getObjectId());
					List<BmobQuery<Collection>> queries = new ArrayList<BmobQuery<Collection>>();
					queries.add(q1);
					queries.add(q2);
					BmobQuery<Collection> mQuery = new BmobQuery<Collection>();
					mQuery.and(queries);
					mQuery.findObjects(getApplicationContext(), new FindListener<Collection>() {

						@Override
						public void onSuccess(List<Collection> arg0) {

							Collection c = new Collection();
							c.delete(getApplicationContext(), arg0.get(0).getObjectId(), new DeleteListener() {

								@Override
								public void onSuccess() {
									Log.i("取消收藏", "取消收藏success");

								}

								@Override
								public void onFailure(int arg0, String arg1) {
									Log.i("取消收藏", "取消收藏fail" + arg1);

								}
							});

						}

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}
					});
				}

			}
		});
		// 发表评论
		btnSendComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newsId = intent.getStringExtra("newsId");
				Log.i(">>>>>>>>newId", newsId);
				String commentContent = etComment.getText().toString();
				if (commentContent.isEmpty()) {
					etComment.setHint("请输入内容");

				} else if (BmobUser.getCurrentUser(getApplicationContext(), _User.class) == null) {
					Intent i = new Intent(NewsDetailActivity.this, LoginActivity.class);
					startActivity(i);
				} else {

					_User user = BmobUser.getCurrentUser(getApplicationContext(), _User.class);
					Log.i("xxxxUserxxxxxx", user.toString());
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
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(getApplicationContext(), "评论失败", Toast.LENGTH_SHORT).show();

						}
					});
				}

			}
		});
		// 查看评论列表
		tvCommentNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(NewsDetailActivity.this, CommentActivity.class);
				i.putExtra("newsId", intent.getStringExtra("newsId"));
				startActivity(i);

			}
		});
		// 查看评论列表
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(NewsDetailActivity.this, CommentActivity.class);
				i.putExtra("newsId", intent.getStringExtra("newsId"));
				startActivity(i);
			}
		});
		// 返回
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

}
