package com.iwebcoding.mobile.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.exolab.castor.xml.Unmarshaller;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.model.EntCfgBean;
import com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig;
import com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig;
import com.iwebcoding.mobile.tool.model.castor.bean.EntityListConfig;
import com.iwebcoding.mobile.tool.model.castor.bean.Platform;

public class EntCfgUtil {
	private static EntCfgUtil instance = null;
	private static List<String> appVerList = new ArrayList<String>();
	private static List<String> platformList = new ArrayList<String>();
	private static List<DeviceConfig> devTypeList = new ArrayList<DeviceConfig>();
	private static List<EntCfgBean> entCountryList = new ArrayList<EntCfgBean>();
	private static EntityListConfig config = null;
	private final String CLASSNAME = EntCfgUtil.class.getName();
	
	private EntCfgUtil() {
		
	}
	
	public void init() {
		parseEntityCfg();
	}
	
	private void parseEntityCfg() {
		final String MTHNAME = "";
		
		MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_PARSE_ENTCFG));
		File cfgFile = new File(AppCfgUtil.getAppCfg(App.KEY_APPCFG_ENTCFG));
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(cfgFile));
			config = (EntityListConfig) Unmarshaller.unmarshal(EntityListConfig.class, reader);
		} catch (Exception e) {
			String msg = formatter.format(new String[] {cfgFile.getAbsolutePath()});
			LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
			JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					String msg = formatter.format(new String[] {cfgFile.getAbsolutePath()});
					LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
					JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
	
	static {
		instance = new EntCfgUtil();
	}
	
	public static EntCfgUtil getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> getAppVerList() {
		appVerList.clear();
		
		EntityConfig[] cfgList = config.getEntityConfig();
		for (int i = 0; i < cfgList.length; i++) {
			appVerList.add(cfgList[i].getVersion());
		}
		
		return appVerList;
	}
	
	/**
	 * 
	 * @param appVer
	 * @return
	 */
	public static List<String> getPlatformList(final String appVer) {
		platformList.clear();
		
		EntityConfig[] cfgList = config.getEntityConfig();
		for (int i = 0; i < cfgList.length; i++) {
			if (appVer.equals(cfgList[i].getVersion())) {
				Platform[] platforms = cfgList[i].getPlatformConfigs().getPlatfrom();
				for (int j = 0; j < platforms.length; j++) {
					platformList.add(platforms[j].getName());
				}
			}
		}
		
		return platformList;
	}
	
	/**
	 * 
	 * @param appVer
	 * @param platform
	 * @return
	 */
	public static List<DeviceConfig> getDevTypeList(final String appVer, String platform) {
		devTypeList.clear();
		
		EntityConfig[] cfgList = config.getEntityConfig();
		for (int i = 0; i < cfgList.length; i++) {
			if (appVer.equals(cfgList[i].getVersion())) {
				Platform[] platforms = cfgList[i].getPlatformConfigs().getPlatfrom();
				for (int j = 0; j < platforms.length; j++) {
					if (platform.equals(platforms[j].getName())) {
						DeviceConfig[] devCfgs = platforms[j].getDeviceConfigs().getDeviceConfig();
						for (int k = 0; k < devCfgs.length; k++) {
							devTypeList.add(devCfgs[k]);
						}
					}
				}
			}
		}
		
		return devTypeList;
	}
	
	/**
	 * 
	 * @param cfgUrl
	 * @return
	 */
	public static List<EntCfgBean> getEntCountryCfgList(final String cfgUrl) {
		entCountryList.clear();
		
		entCountryList = FileUtil.getEntCountryCfgList(cfgUrl);
		
		return entCountryList;
	}
}
