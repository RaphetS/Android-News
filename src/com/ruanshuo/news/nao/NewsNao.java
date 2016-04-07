package com.ruanshuo.news.nao;

import java.util.List;

import com.ruanshuo.news.bean.NewsItem;

public interface NewsNao {
	public List<NewsItem> getNewsItem(int newsType,int currentPage);
}
