package com.iwebcoding.mobile.tool.framework;

import com.iwebcoding.mobile.tool.service.AbstractService;

public interface DataPageController extends Controller {
	/**
	 * 
	 * @return
	 */
	AbstractService getServcie();
	
	/**
	 * 
	 * @return
	 */
	DataPageView getPageView();
	
	/**
	 * 
	 */
	void onRefresh();
}
