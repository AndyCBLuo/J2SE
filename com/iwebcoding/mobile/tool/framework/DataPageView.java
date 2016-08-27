package com.iwebcoding.mobile.tool.framework;

import java.awt.Component;

public interface DataPageView extends View {
	/**
	 * 
	 * @param controller
	 */
	void init(DataPageController controller);
	
	/**
	 * 
	 * @return
	 */
	Component asComponet();
	
	/**
	 * 
	 * @return
	 */
	DataPageController getController();
	
	/**
	 * 
	 */
	void refreshData();
}
