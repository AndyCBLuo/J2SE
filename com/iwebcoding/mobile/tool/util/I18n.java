package com.iwebcoding.mobile.tool.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.iwebcoding.mobile.tool.constant.App;

public enum I18n {
	APPCFG("appCfg"),
	APPVIEW("appView"),
	DEPLOY("deployCfg"),
	CHKSUM("genChksum"),
	JVIEWER("jsonViewer"),
	PROGRESS("progress"),
	SUBJSON("subJson"),
	AUTH("auth"),
	CONTACT("contact"),
	SETTING("setting"),
	ERROR("error");
	
	private String bundleName = null;
	
	/**
	 * 
	 * @param name
	 */
	private I18n(String name) {
		bundleName = App.NLS_BASE + name;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = null;
		
		try {
			value = ResourceBundle.getBundle(bundleName).getString(key);
		} catch (MissingResourceException e) {
			value = "#MissingResource#";
		}
		
		return value;
	}
}
