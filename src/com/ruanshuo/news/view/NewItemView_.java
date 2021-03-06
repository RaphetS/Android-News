package com.ruanshuo.news.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

import com.ruanshuo.news.R;
import com.ruanshuo.news.R.id;

/**
 * We use @SuppressWarning here because our java code generator doesn't know
 * that there is no need to import OnXXXListeners from View as we already are in
 * a View.
 * 
 */
@SuppressWarnings("unused")
public final class NewItemView_ extends NewItemView implements HasViews, OnViewChangedListener {

	private boolean alreadyInflated_ = false;
	private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

	public NewItemView_(Context context) {
		super(context);
		init_();
	}

	public static NewItemView build(Context context) {
		NewItemView_ instance = new NewItemView_(context);
		instance.onFinishInflate();
		return instance;
	}

	/**
	 * The mAlreadyInflated_ hack is needed because of an Android bug which
	 * leads to infinite calls of onFinishInflate() when inflating a layout with
	 * a parent and using the <merge /> tag.
	 * 
	 */
	@Override
	public void onFinishInflate() {
		if (!alreadyInflated_) {
			alreadyInflated_ = true;
			inflate(getContext(), R.layout.item_news, this);
			onViewChangedNotifier_.notifyViewChanged(this);
		}
		super.onFinishInflate();
	}

	private void init_() {
		OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
		OnViewChangedNotifier.registerOnViewChangedListener(this);
		OnViewChangedNotifier.replaceNotifier(previousNotifier);
	}

	@Override
	public void onViewChanged(HasViews hasViews) {
		leftImage = ((ImageView) hasViews.findViewById(id.left_image));
		itemAbstract = ((TextView) hasViews.findViewById(id.item_abstract));
		item_image0 = ((ImageView) hasViews.findViewById(id.item_image_0));
		articleLayout = ((RelativeLayout) hasViews.findViewById(id.article_top_layout));
		imageLayout = ((LinearLayout) hasViews.findViewById(id.layout_image));
		item_image2 = ((ImageView) hasViews.findViewById(id.item_image_2));
		itemTitle = ((TextView) hasViews.findViewById(id.item_title));
		itemConten = ((TextView) hasViews.findViewById(id.item_content));
		item_image1 = ((ImageView) hasViews.findViewById(id.item_image_1));
	}

}
