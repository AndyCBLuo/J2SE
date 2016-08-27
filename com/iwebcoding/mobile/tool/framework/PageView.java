package com.iwebcoding.mobile.tool.framework;

import java.awt.Component;


public interface PageView extends View {
	/**
	 * 
	 * @param controller
	 */
	void init(PageController controller);
	
	/**
	 * 
	 * @return
	 */
	Component asComponet();
	
	/**
	 * 
	 * @return
	 */
	PageController getController();
}
