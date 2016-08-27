package com.iwebcoding.mobile.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;

public class SysCfgUtil {
	private static SysCfgUtil instance = null;
	private static PropertiesConfiguration configuration = null;
	private static PropertiesConfigurationLayout cfgLayout = null;
	private static final String CLASSNAME = SysCfgUtil.class.getName();
	
	private SysCfgUtil() {
		
	}
	
	public static SysCfgUtil getInstance() {
		return instance;
	}
	
	public void init() {
		final String MTHNAME = "init()";
		
		MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_PARSE_SYSCFG));
		configuration = new PropertiesConfiguration();
		cfgLayout = new PropertiesConfigurationLayout();
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(AppCfgUtil.getAppCfg(App.KEY_APPCFG_SYSCFG)), App.UTF8);
			cfgLayout.load(configuration, reader);
		} catch (Exception e) {
			String msg = formatter.format(new String[] {AppCfgUtil.getAppCfg(App.KEY_APPCFG_SYSCFG)});
			LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
			JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static String getSystemConfig(String key) {
		return (String) configuration.getProperty(key);
	}
	
	public static void updateSystemConfig(String key, String value) {
		configuration.setProperty(key, value);
	}
	
	public static void saveSystemSettings() {
		final String MTHNAME = "saveSystemSettings()";
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(AppCfgUtil.getAppCfg(App.KEY_APPCFG_SYSCFG)));
			cfgLayout.save(configuration, writer);
		} catch (Exception e) {
			
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_CLOSE_STREAM), e);
				}
			}
		}
	}
	
	static {
		instance = new SysCfgUtil();
	}
}
