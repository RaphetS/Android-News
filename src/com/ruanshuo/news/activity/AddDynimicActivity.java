package com.ruanshuo.news.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bmob.BmobProFile;
import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.GridViewAdapter;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.DynimicBean;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.utils.ImageTools;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class AddDynimicActivity extends BaseActivity {
	private List<String> pics = new ArrayList<String>();
	private EditText etContent;
	private GridView gridView;
	private Button btnSend;
	private ImageButton btnBack;
	private _User currentUser;
	private PopupWindow popWinow;
	private RelativeLayout rl;
	private GridViewAdapter adapter;
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private View parentView;
	private List<String> photos = new ArrayList<String>();
	private ProgressDialog dialog;

	@Override
	public void initViews() {
		parentView = getLayoutInflater().inflate(R.layout.activity_add_dynimic, null);
		setContentView(parentView);
		etContent = (EditText) findViewById(R.id.et_dynimic_content);
		btnSend = (Button) findViewById(R.id.btn_send_dynimic);
		btnBack = (ImageButton) findViewById(R.id.imgbtn_back_send_dynimic);
		gridView = (GridView) findViewById(R.id.gridView);

	}

	@Override
	public void initDatas() {
		initPopWindow();
		currentUser = BmobUser.getCurrentUser(getApplicationContext(), _User.class);
		adapter = new GridViewAdapter(getApplicationContext(), pics);
		gridView.setAdapter(adapter);
	}

	@SuppressWarnings("deprecation")
	private void initPopWindow() {
		popWinow = new PopupWindow(AddDynimicActivity.this);
		View view = getLayoutInflater().inflate(R.layout.popup_window, null);

		popWinow.setWidth(LayoutParams.MATCH_PARENT);
		popWinow.setHeight(LayoutParams.WRAP_CONTENT);
		popWinow.setBackgroundDrawable(new BitmapDrawable());
		popWinow.setFocusable(true);
		popWinow.setOutsideTouchable(true);
		popWinow.setContentView(view);

		Button btnTakePhoto = (Button) view.findViewById(R.id.btn_take_photo_window);
		Button btnChoosePhoto = (Button) view.findViewById(R.id.btn_slect_photo_window);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancle_window);
		rl = (RelativeLayout) view.findViewById(R.id.rl_popup_window);
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWinow.dismiss();

			}
		});
		btnTakePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ImageTools.checkSDCardAvailable()) {
					popWinow.dismiss();
					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intentFromCapture, 1);

				}

			}
		});
		btnChoosePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popWinow.dismiss();
				Intent intentFromGallery = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(intentFromGallery, 2);

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWinow.dismiss();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case 1:
				String fileName = String.valueOf(System.currentTimeMillis()) + ".png";
				Log.e("picName111", fileName);
				Bitmap bm = (Bitmap) data.getExtras().get("data");

				ImageTools.saveBitmap(bm, fileName, "/sdcard/news/photo/");
				photos.add("/sdcard/news/photo/" + fileName);
				String path1 = "file:///sdcard/news/photo/" + fileName;
				Log.e("picName22222222", path1);

				adapter.addData(path1);

				adapter.notifyDataSetChanged();
				break;

			case 2:
				Uri uri = data.getData();
				if (uri != null) {
					String path2 = "content://media" + uri.getPath();
					String strRingPath = ImageTools.getRealFilePath(getApplicationContext(), uri);
					photos.add(strRingPath);

					Log.e("6666666666", path2);
					adapter.addData(path2);
					adapter.notifyDataSetChanged();
				}
				/*
				 * 或者 Bundle bundle=data.getExtras(); Bitmap bmp=(Bitmap)
				 * bundle.get("data");
				 */
				break;
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void initListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position == adapter.getCount() - 1) {
					popWinow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

				}
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etContent.getText() == null || etContent.getText().toString().isEmpty()) {
					etContent.setHint("说的什么吧");
				} else {

					String[] ps = new String[photos.size()];
					for (int i = 0; i < ps.length; i++) {
						ps[i] = photos.get(i);
					}
					BmobProFile.getInstance(getApplicationContext()).uploadBatch(ps,
							new com.bmob.btp.callback.UploadBatchListener() {

						@Override
						public void onError(int arg0, String arg1) {
							System.out.println("上传失败" + arg1);
						}

						@Override
						public void onSuccess(boolean arg0, String[] arg1, String[] arg2, BmobFile[] arg3) {
							String dynimicContent = etContent.getText().toString();
							DynimicBean dynimicBean = new DynimicBean();
							dynimicBean.setUser(currentUser);
							dynimicBean.setDynimicContent(dynimicContent);
							List<String> picList = new ArrayList<String>();
							for (int i = 0; i < arg2.length; i++) {

								String url = BmobProFile.getInstance(getApplicationContext()).signURL(arg1[i], arg2[i],
										"538ffdd81c7b1fe60f6ec0ebb30d8f35", 0, null);
								picList.add(url);
							}
							dynimicBean.setDynimicPics(picList);
							dynimicBean.save(getApplicationContext(), new SaveListener() {

								@Override
								public void onSuccess() {

									Intent intent = new Intent(AddDynimicActivity.this, DynimicActivity.class);
									startActivity(intent);
								}

								@Override
								public void onFailure(int arg0, String arg1) {
									System.out.println("发表失败"+arg1);
								}
							});
						}

						@Override
						public void onProgress(int arg0, int arg1, int arg2, int arg3) {

						}
					});
				}

			}
		});
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

}
