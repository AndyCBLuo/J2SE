package com.iwebcoding.mobile.tool.framework;

import com.iwebcoding.mobile.tool.service.AbstractService;

public abstract class AbstractDataPageController implements DataPageController {
	/**
	 * 
	 */
	protected DataPageView pageView;
	
	/**
	 * 
	 */
	protected AbstractService service;
	
	/**
	 * 
	 * @return
	 */
	protected abstract AbstractService createService();
	
	/**
	 * 
	 * @return
	 */
	protected abstract DataPageView createPageView();
	
	@Override
	public AbstractService getServcie() {
		if (service == null) {
			service = createService();
		}
		
		return service;
	}

	@Override
	public DataPageView getPageView() {
		if (pageView == null) {
			pageView = createPageView();
			pageView.init(this);
		}
		
		return pageView;
	}

	@Override
	public void onRefresh() {
		if (pageView == null) {
			pageView = createPageView();
			pageView.init(this);
			pageView.refreshData();
		} else {
			pageView.refreshData();
		}
	}
}
