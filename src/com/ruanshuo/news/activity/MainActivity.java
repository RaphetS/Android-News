package com.ruanshuo.news.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.fragment.BaseNewsFragment;
import com.ruanshuo.news.fragment.CommunityFragment;
import com.ruanshuo.news.fragment.ImageFragment;
import com.ruanshuo.news.fragment.NewsFragment;
import com.ruanshuo.news.fragment.SettingFragment;
import com.ruanshuo.news.http.Url;
import com.ruanshuo.news.utils.ImageTools;
import com.ruanshuo.news.view.CircleImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends SlidingFragmentActivity {
	private RadioGroup radioGroup;
	private ViewPager viewPager;
	private List<Fragment> listfragments;
	private TextView tvUsername;
	private _User user;
	private LinearLayout ll;
	private TextView tvTitle;
	private CircleImageView civ;
	private CircleImageView civLeftMenuHead;
	private ListView lvLeftMenu;
	private String[] leftMenus = new String[] { "我的消息", "我的动态", "我的收藏", "退出登录" };
	private String[] choicePicMenu = new String[] { "拍照", "选择本地图片" };
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private String picName;
	private long exitTime = 0;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// 初始化UI
		initUI();
		// 初始化数据
		initData();
		// 设置监听事件
		setClickListener();
	}

	/*
	 * 初始化ui以及findview
	 */
	private void initUI() {
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.fragment_left_sliding_menu);

		// 侧滑栏设置
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.LEFT);
		slidingMenu.setBehindOffset(200);
		slidingMenu.setFadeDegree(0.5f);

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		viewPager = (ViewPager) findViewById(R.id.vp_main);
		tvUsername = (TextView) findViewById(R.id.tv_username);
		ll = (LinearLayout) findViewById(R.id.ll_user_left_menu);
		tvTitle = (TextView) findViewById(R.id.tv_title_fragment);
		civ = (CircleImageView) findViewById(R.id.civ_left_sliding);
		civLeftMenuHead = (CircleImageView) findViewById(R.id.civ_leftmenu_head);
		lvLeftMenu = (ListView) findViewById(R.id.lv_leftmenu);
	}

	/*
	 * 初始化数据
	 */
	private void initData() {
		tvTitle.setText("首页");
		user = BmobUser.getCurrentUser(getApplicationContext(), _User.class);

		if (user == null) {
			tvUsername.setText("请登录");

			ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					intent.putExtra("msg", "login");
					startActivity(intent);
					finish();

				}
			});
		} else {
			picName = user.getUsername() + ".png";
			tvUsername.setText(user.getUsername());
			ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showDialog();
				}
			});
			if (user.getPic() != null) {
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.default_round_head)
						.showImageOnFail(R.drawable.default_round_head).cacheInMemory(true).cacheOnDisk(true).build();

				ImageLoader.getInstance().displayImage(user.getPic(), civLeftMenuHead, options);
				ImageLoader.getInstance().displayImage(user.getPic(), civ, options);
			}

		}

		listfragments = new ArrayList<Fragment>();

		// HotFragment hotFragment = new HotFragment();
		NewsFragment newsFragment = new NewsFragment();
		CommunityFragment communityFragment = new CommunityFragment();
		SettingFragment settingFragment = new SettingFragment();
		ImageFragment imageFragment=new ImageFragment();

		listfragments.add(newsFragment);
		listfragments.add(imageFragment);
		listfragments.add(communityFragment);
		listfragments.add(settingFragment);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), listfragments));
		viewPager.setCurrentItem(0);

		ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,
				leftMenus);
		lvLeftMenu.setAdapter(adapter);
	}

	/*
	 * 显示选择头像菜单对话框
	 */
	protected void showDialog() {
		new AlertDialog.Builder(this).setTitle("设置头像")
				.setItems(choicePicMenu, new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							if (hasSDcard()) {
								intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment.getExternalStorageDirectory(), picName)));
								startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
							}
							break;

						case 1:
							Intent intentFromGallery = new Intent(Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

							startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
							break;
						}
					}

				}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				}).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case CAMERA_REQUEST_CODE:
				if (hasSDcard()) {
					File tempFile = new File(Environment.getExternalStorageDirectory(), picName);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();

				}

				break;

			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					setImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * 保存裁剪之后图片的数据
	 */
	private void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			civLeftMenuHead.setImageBitmap(photo);
			if (hasSDcard()) {

				String url = "/sdcard/news/head/";
				ImageTools.saveBitmap(photo, picName, url);
				BTPFileResponse response = BmobProFile.getInstance(getApplicationContext()).upload(url + picName,
						new UploadListener() {

							@Override
							public void onError(int arg0, String arg1) {

								Toast.makeText(getApplicationContext(), "设置头像失败" + arg1, Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onSuccess(String fileName, String fileUrl, BmobFile bmobFile) {
								Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
								String URL = BmobProFile.getInstance(getApplicationContext()).signURL(fileName, fileUrl,
										"538ffdd81c7b1fe60f6ec0ebb30d8f35", 0, null);
								_User u = new _User();
								u.setPic(URL);
								u.update(getApplicationContext(), user.getObjectId(), new UpdateListener() {

									@Override
									public void onSuccess() {

										Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
									}

									@Override
									public void onFailure(int arg0, String arg1) {
										Toast.makeText(getApplicationContext(), "fail" + arg1, Toast.LENGTH_SHORT)
												.show();

									}
								});

							}

							@Override
							public void onProgress(int arg0) {

							}
						});
			}
		}

	}

	/*
	 * 图片裁剪
	 */
	private void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Toast.makeText(getApplicationContext(), "选择图片出错！", Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	/*
	 * 设置监听器
	 */
	private void setClickListener() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_news:
					viewPager.setCurrentItem(0, false);
					tvTitle.setText("新闻中心");
					break;

				case R.id.rb_hot:
					viewPager.setCurrentItem(1, false);
					tvTitle.setText("图片新闻");
					break;
				case R.id.rb_community:

					viewPager.setCurrentItem(2, false);
					tvTitle.setText("社区动态");
					break;
				case R.id.rb_setting:
					viewPager.setCurrentItem(3, false);
					tvTitle.setText("设置中心");
					break;
				}

			}
		});

		civ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SlidingMenu slidingMenu = getSlidingMenu();
				slidingMenu.toggle();

			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				listfragments.get(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		/*
		 * 侧滑菜单监听
		 */
		lvLeftMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				switch (arg2) {
				// 我的消息

				case 0:
					if (user == null) {
						Intent i = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(i);
					} else {

						Intent intent = new Intent(MainActivity.this, MyMessageActivity.class);
						startActivity(intent);
					}
					break;
				// 我的动态
				case 1:
					break;
				// 我的收藏
				case 2:
					if (user != null) {
						Intent i3 = new Intent(MainActivity.this, MyCollectionActivity.class);
						startActivity(i3);
					} else {
						Intent i = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(i);
					}

					break;
				// 退出登录
				case 3:
					if (user != null) {

						BmobUser.logOut(getApplicationContext());
						Intent i4 = new Intent(MainActivity.this, LoginActivity.class);
						i4.putExtra("msg", "logout");
						startActivity(i4);
						finish();
					}
					break;

				}

			}
		});

	}

	/*
	 * 判断是否存在Sd卡
	 */
	private boolean hasSDcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * 双击退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (KeyEvent.KEYCODE_BACK == keyCode) {
			// 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				// 将系统当前的时间赋值给exitTime
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * 主界面的fragment的适配器
	 */
	private class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			listfragments = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return listfragments.get(arg0);
		}

		@Override
		public int getCount() {
			return listfragments.size();
		}

	}
}
