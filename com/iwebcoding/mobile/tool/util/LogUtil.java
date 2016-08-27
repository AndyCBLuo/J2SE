package com.iwebcoding.mobile.tool.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;

public class LogUtil {
	private static final String CLASSNAME = LogUtil.class.getCanonicalName();
	
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	private static LogUtil instance = null;
	
	public void init() {
		InputStream inStream = LogUtil.class.getClassLoader().getResourceAsStream(App.CFG_PATH_LOGGER);
		try {
			LogManager.getLogManager().readConfiguration(inStream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.TITLE_ERR), 
					I18n.ERROR.getString(ErrNLS.MSG_INIT_LOG), JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.TITLE_ERR), 
						I18n.ERROR.getString(ErrNLS.MSG_INIT_LOG), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private LogUtil() {
		
	}
	
	public static LogUtil getInstance() {
		return instance;
	}
	
	static {
		instance = new LogUtil();
	}
	
	public static void logInfo(String className, String methodName, String msg) {
		LOGGER.logp(Level.INFO, className, methodName, msg);
	}
	
	public static void logError(String className, String methodName, String msg, Throwable t) {
		LOGGER.logp(Level.SEVERE, className, methodName, msg, t);
	}
}
