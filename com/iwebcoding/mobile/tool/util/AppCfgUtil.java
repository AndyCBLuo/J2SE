package com.iwebcoding.mobile.tool.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;

public class AppCfgUtil {
	private static AppCfgUtil instance = null;
	private static Properties appCfsProp = null;
	private final String CLASSNAME = AppCfgUtil.class.getName();
	
	private AppCfgUtil() {
		
	}
	
	public static AppCfgUtil getInstance() {
		return instance;
	}
	
	public void init() {
		final String MTHNAME = "init()";
		
		MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_PARSE_APPCFG));
		appCfsProp = new Properties();
		try {
			appCfsProp.load(AppCfgUtil.class.getClassLoader().getResourceAsStream(App.CFG_PATH_APP));
		} catch (IOException e) {
			String msg = formatter.format(new String[] {App.CFG_PATH_APP});
			LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
			JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static String getAppCfg(String key) {
		return appCfsProp.getProperty(key, key);
	}
	
	static {
		instance = new AppCfgUtil();
	}
}
