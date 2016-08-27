package com.iwebcoding.mobile.tool.framework;

public abstract class AbstractPageController implements PageController {
	/**
	 * 
	 */
	protected PageView pageView;
	
	/**
	 * 
	 * @return
	 */
	protected abstract PageView createPageView();
	
	/**
	 * 
	 */
	@Override
	public PageView getPageView() {
		if (pageView == null) {
			pageView = createPageView();
			pageView.init(this);
		}
		
		return pageView;
	}
}
