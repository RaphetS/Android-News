package com.ruanshuo.news.view;

import com.ruanshuo.news.R;
import com.ruanshuo.news.R.id;

import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * We use @SuppressWarning here because our java code generator doesn't know
 * that there is no need to import OnXXXListeners from View as we already are in
 * a View.
 * 
 */
@SuppressWarnings("unused")
public final class NewImageView_ extends NewImageView implements HasViews, OnViewChangedListener {

	private boolean alreadyInflated_ = false;
	private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

	public NewImageView_(Context context) {
		super(context);
		init_();
	}

	public static NewImageView build(Context context) {
		NewImageView_ instance = new NewImageView_(context);
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
			inflate(getContext(), R.layout.item_image, this);
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
		progressButton = ((ProgressButton) hasViews.findViewById(id.progressButton));
		currentPage = ((TextView) hasViews.findViewById(id.current_page));
		currentImage = ((ImageView) hasViews.findViewById(id.current_image));
		initView();
	}

}
