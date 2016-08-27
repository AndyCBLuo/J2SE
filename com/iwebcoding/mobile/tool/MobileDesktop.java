package com.iwebcoding.mobile.tool;

import java.awt.Font;
import java.util.Enumeration;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.iwebcoding.mobile.tool.component.Splash;
import com.iwebcoding.mobile.tool.util.AppCfgUtil;
import com.iwebcoding.mobile.tool.util.DeployUtil;
import com.iwebcoding.mobile.tool.util.EntCfgUtil;
import com.iwebcoding.mobile.tool.util.FileUtil;
import com.iwebcoding.mobile.tool.util.LogUtil;
import com.iwebcoding.mobile.tool.util.SysCfgUtil;
import com.iwebcoding.mobile.tool.util.ViewHelper;
import com.iwebcoding.mobile.tool.view.AppView;


public class MobileDesktop {
	private static final String CLASSNAME = MobileDesktop.class.getName();
	private static Splash splash;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initAppData();
		applyViewSettings();
		displaySplash();
		disposeSplash();
		launchAppView();
	}
	
	/**
	 * 
	 */
	private static void applyViewSettings() {
		setSystemLookAndFeel();
		setSystemFontStyle();
		setSystemLocale();
	}
	
	/**
	 * 
	 */
	private static void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(ViewHelper.getLookAndFeelClassName());
		} catch (Exception lafe) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.exit(0);
			}
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
	}
	
	/**
	 * 
	 */
	private static void setSystemFontStyle() {
		Enumeration<Object> uiObjKeys = UIManager.getDefaults().keys();
		while (uiObjKeys.hasMoreElements()) {
			Object uiKey = uiObjKeys.nextElement();
			Object uiObj = UIManager.get(uiKey);
			if (uiObj instanceof FontUIResource) {
				FontUIResource curFont = (FontUIResource) uiObj;
				Font newFont = new Font(ViewHelper.getSysFontConfig(), Font.PLAIN, curFont.getSize());
				UIManager.put(uiKey, newFont);
			}
		}
	}

	/**
	 * 
	 */
	private static void setSystemLocale() {
		Locale.setDefault(ViewHelper.getSysCfgLocale());
	}
	
	/**
	 * 
	 */
	private static void initAppData() {
		LogUtil.getInstance().init();
		AppCfgUtil.getInstance().init();
		EntCfgUtil.getInstance().init();
		SysCfgUtil.getInstance().init();
		FileUtil.getInstance().init();
		DeployUtil.getInstance().init();
	}
	
	/**
	 * 
	 */
	private static void displaySplash() {
		splash = new Splash();
		splash.setVisible(true);
	}
	
	/**
	 * 
	 */
	private static void disposeSplash() {
		final String MTHNAME = "";
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			LogUtil.logError(CLASSNAME, MTHNAME, "Interrupted exception when thread sleep.", e);
		}
		splash.dispose();
	}
	
	/**
	 * 
	 */
	private static void launchAppView() {
		AppView appView = new AppView();
		appView.setVisible(true);
	}
}
